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
import com.depromeet.team5.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    @Transactional
    public void saveStore(StoreDto storeDto, Long userId){
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<Image> image = new ArrayList<>();

        Store store = Store.from(storeDto, image, user);
        log.info(storeRepository.findAll().toString());
        storeRepository.save(store);
    }

    @Override
    @Transactional
    public List<StoreCardDto> getAll(Long latitude, Long longitude, Long radius) {
        List<StoreCardDto> storeList= storeRepository.findAllByAddress(latitude, longitude, radius)
                .stream()
                .map(StoreCardDto::from)
                .collect(Collectors.toList());
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
    public void deleteStore(Long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(StoreNotFoundException::new);
        storeRepository.delete(store);
    }




}
