package com.depromeet.team5.service.impl;

import com.depromeet.team5.domain.ImageUploadValue;
import com.depromeet.team5.domain.store.*;
import com.depromeet.team5.domain.user.User;
import com.depromeet.team5.exception.StoreNotFoundException;
import com.depromeet.team5.exception.StoreDeleteRequestDuplicatedException;
import com.depromeet.team5.exception.UserNotFoundException;
import com.depromeet.team5.repository.DeleteRepository;
import com.depromeet.team5.repository.StoreRepository;
import com.depromeet.team5.repository.UserRepository;
import com.depromeet.team5.service.S3FileUploadService;
import com.depromeet.team5.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final DeleteRepository deleteRepository;
    private final S3FileUploadService s3FileUploadService;

    @Override
    @Transactional
    public Store saveStore(StoreCreateValue storeCreateValue, Long userId, List<ImageUploadValue> imageUploadValues) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        List<Image> image = convertImage(imageUploadValues);
        Store store = Store.from(storeCreateValue, image, user);
        return storeRepository.save(store);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Store> getAll(Double latitude, Double longitude) {
        return storeRepository.findAllByAddress(latitude, longitude);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Store> getAllByUser(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return storeRepository.findAllByUser(user, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Store getDetail(Long storeId, Double latitude, Double longitude) {
        return storeRepository.findById(storeId).orElseThrow(() -> new StoreNotFoundException(storeId));
    }

    @Override
    @Transactional
    public void updateStore(StoreUpdateValue storeUpdateValue, Long storeId, List<ImageUploadValue> imageUploadValues) {
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new StoreNotFoundException(storeId));
        List<Image> image = convertImage(imageUploadValues);
        store.setStore(storeUpdateValue, image);
        storeRepository.save(store);
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

    private List<Image> convertImage(List<ImageUploadValue> imageUploadValues) {
        return imageUploadValues.stream()
                .filter(Objects::nonNull)
                .map(s3FileUploadService::upload)
                .map(Image::from)
                .collect(Collectors.toList());
    }
}
