package com.depromeet.team5.service.impl;

import com.depromeet.team5.domain.Image;
import com.depromeet.team5.domain.Store;
import com.depromeet.team5.domain.User;
import com.depromeet.team5.dto.StoreCardDto;
import com.depromeet.team5.dto.StoreDto;
import com.depromeet.team5.exception.StoreNotFoundException;
import com.depromeet.team5.exception.UserNotFoundException;
import com.depromeet.team5.repository.StoreRepository;
import com.depromeet.team5.repository.UserRepository;
import com.depromeet.team5.service.S3FileUploadService;
import com.depromeet.team5.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final S3FileUploadService s3FileUploadService;

    @Override
    @Transactional
    public void saveStore(StoreDto storeDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<Image> image = convertImage(storeDto.getImage());

        Store store = Store.from(storeDto, image, user);
        log.info(storeRepository.findAll().toString());
        storeRepository.save(store);
    }

    @Override
    @Transactional
    public List<StoreCardDto> getAll(Float latitude, Float longitude) {
        List<StoreCardDto> storeList = storeRepository.findAllByAddress(latitude, longitude)
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
    public Store getDetail(Long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(StoreNotFoundException::new);
        return store;
    }

    @Override
    @Transactional
    public void updateStore(StoreDto storeDto, Long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(StoreNotFoundException::new);
        List<Image> image = convertImage(storeDto.getImage());
        store.setStore(storeDto, image);
        storeRepository.save(store);
    }


    @Override
    @Transactional
    public void deleteStore(Long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(StoreNotFoundException::new);
        storeRepository.delete(store);
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
