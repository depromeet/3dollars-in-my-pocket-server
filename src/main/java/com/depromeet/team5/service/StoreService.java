package com.depromeet.team5.service;

import com.depromeet.team5.dto.StoreDto;

public interface StoreService {
    void saveStore(StoreDto storeDto, Long userId);
    void deleteStore(Long storeId);
}
