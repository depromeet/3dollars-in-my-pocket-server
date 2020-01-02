package com.depromeet.team5.service;

import com.depromeet.team5.domain.Store;
import com.depromeet.team5.dto.StoreCardDto;
import com.depromeet.team5.dto.StoreDto;

import java.util.List;

public interface StoreService {
    void saveStore(StoreDto storeDto, Long userId);
    List<StoreCardDto> getAll(Long latitude, Long longitude, Long radius);
    Store getDetail(Long storeId);
    //update
    void deleteStore(Long storeId);
}
