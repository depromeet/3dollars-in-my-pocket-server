package com.depromeet.team5.service.impl;

import com.depromeet.team5.domain.*;
import com.depromeet.team5.dto.*;
import com.depromeet.team5.exception.StoreNotFoundException;
import com.depromeet.team5.exception.UserIdCheckException;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public StoreIdDto saveStore(StoreDto storeDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<Image> image = convertImage(storeDto.getImage());
        Store store = Store.from(storeDto, image, user);
        storeRepository.save(store);

        StoreIdDto storeIdDto = new StoreIdDto();
        storeIdDto.setStoreId(store.getId());
        return storeIdDto;
    }

    @Override
    @Transactional
    public List<StoreCardDto> getAll(Double latitude, Double longitude, Pageable pageable) {
        List<StoreCardDto> storeList = storeRepository.findAllByAddress(latitude, longitude, pageable)
                .getContent()
                .stream()
                .map(StoreCardDto::from)
                .collect(Collectors.toList());

        for (StoreCardDto storeCardDto : storeList) {
            StoreCardDto.calculationDistance(storeCardDto, latitude, longitude);
        }
        return storeList;
    }

    @Override
    @Transactional
    public List<StoreMyPageDto> getAllByUser(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return storeRepository.findAllByUser(user, pageable)
                .getContent()
                .stream()
                .map(StoreMyPageDto::from)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public StoreDetailDto getDetail(Long storeId, Double latitude, Double longitude) {
        Store store = storeRepository.findById(storeId).orElseThrow(StoreNotFoundException::new);
        StoreDetailDto storeDetailDto = StoreDetailDto.from(store);
        StoreDetailDto.calculationDistance(storeDetailDto, latitude, longitude);
        return storeDetailDto;
    }

    @Override
    @Transactional
    public void updateStore(StoreUpdateDto storeUpdateDto, Long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(StoreNotFoundException::new);
        List<Image> image = convertImage(storeUpdateDto.getImage());
        store.setStore(storeUpdateDto, image);
        storeRepository.save(store);
    }

    @Override
    @Transactional
    public void deleteStore(Long storeId, Long userId, DeleteReasonType deleteReasonType) {
        userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Store store = storeRepository.findById(storeId).orElseThrow(StoreNotFoundException::new);

        if (deleteRepository.findByUserIdAndStoreId(userId, storeId).isPresent()) {
            throw new UserIdCheckException();
        } else if (deleteRepository.findAllByStoreIdAndReason(storeId, deleteReasonType).size() == 2) {
            storeRepository.delete(store);
        } else {
            store.addDeleteId(storeId, userId, deleteReasonType);
        }
    }

    private List<Image> convertImage(List<MultipartFile> multipartFileList) {
        List<Image> image = new ArrayList<>();
        try {
            if (multipartFileList != null) {
                for (MultipartFile multipartFile : multipartFileList) {
                    Image image1 = new Image();
                    image1.setUrl(s3FileUploadService.upload(multipartFile));
                    image.add(image1);
                }
            }

        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return image;
    }
}
