package com.depromeet.team5.service.impl;

import com.depromeet.team5.domain.Store;
import com.depromeet.team5.domain.User;
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
        Store store = Store.from(storeDto, user);
        storeRepository.save(store);
    }

//    @Override
//    @Transactional
//    public void updateStore(StoreDto storeDto, Long userId, Long storeId) {
//        userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
//        Store store = storeRepository.findById(storeId).orElseThrow(StoreNotFoundException::new);
////        Store.setStore()
//    }

    @Override
    @Transactional
    public void deleteStore(Long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(StoreNotFoundException::new);
        storeRepository.delete(store);
    }


}
