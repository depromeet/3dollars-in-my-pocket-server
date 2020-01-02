package com.depromeet.team5.service;

import com.depromeet.team5.domain.Store;
import com.depromeet.team5.dto.StoreDto;

import java.util.List;

public interface StoreService {
    void saveStore(StoreDto storeDto, Long userId);
    List<Store> getAll();
    Store getDetail(Long storeId);

    void deleteStore(Long storeId);
}
