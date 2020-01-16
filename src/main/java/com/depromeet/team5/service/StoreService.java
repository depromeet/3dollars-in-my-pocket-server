package com.depromeet.team5.service;

import com.depromeet.team5.dto.*;

import java.util.List;

public interface StoreService {
    StoreDetailDto saveStore(StoreDto storeDto, Long userId);
    List<StoreCardDto> getAll(Double latitude, Double longitude);
    List<StoreMyPageDto> getAllByUser(Long userId);
    StoreDetailDto getDetail(Long storeId);
    void updateStore(StoreUpdateDto storeUpdateDto, Long storeId);
    void deleteStore(Long storeId, Long userId);
}
