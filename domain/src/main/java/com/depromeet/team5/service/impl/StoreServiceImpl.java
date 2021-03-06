package com.depromeet.team5.service.impl;

import com.depromeet.team5.domain.ImageUploadValue;
import com.depromeet.team5.domain.Location;
import com.depromeet.team5.domain.store.*;
import com.depromeet.team5.domain.user.User;
import com.depromeet.team5.exception.StoreDeleteRequestDuplicatedException;
import com.depromeet.team5.exception.StoreNotFoundException;
import com.depromeet.team5.exception.UserNotFoundException;
import com.depromeet.team5.repository.*;
import com.depromeet.team5.service.S3FileUploadService;
import com.depromeet.team5.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final DeleteRepository deleteRepository;
    private final S3FileUploadService s3FileUploadService;
    private final MenuCategoryRepository menuCategoryRepository;
    private final StoreMenuCategoryRepository storeMenuCategoryRepository;
    private final ImageRepository imageRepository;

    @Override
    @Transactional
    public Store saveStore(StoreCreateValue storeCreateValue, Long userId, List<ImageUploadValue> imageUploadValues) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        List<Image> images = convertImages(imageUploadValues);
        imageRepository.saveAll(images);
        Store store = Store.from(storeCreateValue, images, user);
        storeRepository.save(store);
        this.addStoreMenuCategory(store.getId());
        return store;
    }

    /**
     * Preparation work to support multiple categories in a store.
     * dual-write > migration > read > delete old codes > delete old schema
     */
    private void addStoreMenuCategory(Long storeId) {
        Store store = storeRepository.findById(storeId).orElse(null);
        if (store == null) {
            log.error("Failed to add menuCategory to store. storeId: {}", storeId);
            return;
        }
        if (store.getCategory() == null) {
            log.error("Failed to add menuCategory to store. 'category' must not be null. storeId: {}", storeId);
            return;
        }
        String categoryName = store.getCategory().name();
        try {
            MenuCategory menuCategory = menuCategoryRepository.findByEnumName(categoryName).orElse(null);
            if (menuCategory == null) {
                log.error("Failed to add menuCategory to store. menuCategory does not exist. storeId: {}, categoryName: {}",
                        storeId, categoryName);
                return;
            }
            StoreMenuCategory storeMenuCategory = storeMenuCategoryRepository.findById(StoreMenuCategoryId.of(store, menuCategory))
                    .orElseGet(() -> storeMenuCategoryRepository.save(StoreMenuCategory.of(store, menuCategory)));
            store.addStoreMenuCategory(storeMenuCategory);
            storeRepository.save(store);
        } catch (Exception e) {
            log.error("Failed to add menuCategory to store. storeId: {}, categoryName: {}",
                    store.getId(), store.getCategory().name());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Store> getStoresByDistanceBetweenAndCategory(Location location, Double distanceStart, Double distanceEnd, CategoryType categoryType) {
        return storeRepository.findByDistanceBetweenAndCategory(
                location.getLatitude(),
                location.getLongitude(),
                distanceStart,
                distanceEnd,
                categoryType.name()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<Store> getAll(Double latitude, Double longitude) {
        return storeRepository.findAllByAddress(latitude, longitude);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Store> getStoresByLocationAndDistance(Location location, Double distance) {
        return storeRepository.findByLocationAndDistanceLessThan(location.getLatitude(), location.getLongitude(), distance);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Store> getAllByUser(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return storeRepository.findAllByUser(user, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Store getStore(Long storeId) {
        return storeRepository.findById(storeId).orElseThrow(() -> new StoreNotFoundException(storeId));
    }

    @Override
    @Transactional
    public void updateStore(StoreUpdateValue storeUpdateValue, Long storeId, List<ImageUploadValue> imageUploadValues) {
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new StoreNotFoundException(storeId));
        List<Image> images = convertImages(imageUploadValues);
        imageRepository.saveAll(images);
        store.setStore(storeUpdateValue, images);
    }

    @Override
    @Transactional
    public void deleteStore(Long storeId, Long userId, DeleteReasonType deleteReasonType) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new StoreNotFoundException(storeId));

        if (deleteRepository.findByUserIdAndStoreId(userId, storeId).isPresent()) {
            throw new StoreDeleteRequestDuplicatedException(userId, storeId);
        } else if (deleteRepository.findAllByStoreIdAndReason(storeId, deleteReasonType).size() == 2) {
            storeRepository.delete(store);
        } else {
            store.addDeleteId(storeId, userId, deleteReasonType);
        }
    }

    @Override
    @Transactional
    public Image addImage(Long storeId, String imageUrl) {
        Assert.notNull(storeId, "'storeId' must not be null");
        Assert.hasText(imageUrl, "'imageUrl' must not be null, empty or blank");

        Store store = storeRepository.findById(storeId).orElseThrow(() -> new StoreNotFoundException(storeId));
        Image image = Image.from(imageUrl);
        imageRepository.save(image);
        store.addImage(image);
        return image;
    }

    @Override
    @Transactional
    public void deleteImage(Long imageId) {
        imageRepository.findById(imageId)
                .ifPresent(image -> {
                    imageRepository.delete(image);
                    s3FileUploadService.delete(image.getUrl());
                });
    }

    @Override
    @Transactional
    public List<Image> getStoreImages(Long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new StoreNotFoundException(storeId));
        return store.getImages();
    }

    private List<Image> convertImages(List<ImageUploadValue> imageUploadValues) {
        return imageUploadValues.stream()
                .filter(Objects::nonNull)
                .map(s3FileUploadService::upload)
                .map(Image::from)
                .collect(Collectors.toList());
    }
}
