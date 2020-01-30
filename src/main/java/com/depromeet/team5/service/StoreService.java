package com.depromeet.team5.service;

import com.depromeet.team5.domain.DeleteReasonType;
import com.depromeet.team5.domain.Store;
import com.depromeet.team5.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StoreService {
    StoreIdDto saveStore(StoreDto storeDto, Long userId);
    List<StoreCardDto> getAll(Double latitude, Double longitude, Pageable pageable);
    List<StoreMyPageDto> getAllByUser(Long userId, Pageable pageable);
    StoreDetailDto getDetail(Long storeId, Double latitude, Double longitude);
    void updateStore(StoreUpdateDto storeUpdateDto, Long storeId);
    void deleteStore(Long storeId, Long userId, DeleteReasonType deleteReasonType);
}
