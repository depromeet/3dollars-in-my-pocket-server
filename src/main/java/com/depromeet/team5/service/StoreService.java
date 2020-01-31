package com.depromeet.team5.service;

import com.depromeet.team5.domain.DeleteReasonType;
import com.depromeet.team5.dto.*;
import org.springframework.data.domain.Pageable;

public interface StoreService {
    StoreIdDto saveStore(StoreDto storeDto, Long userId);
    StoreCardPomDto getAll(Double latitude, Double longitude, Pageable pageable);
    StoreMyPagePomDto getAllByUser(Long userId, Pageable pageable);
    StoreDetailDto getDetail(Long storeId, Double latitude, Double longitude);
    void updateStore(StoreUpdateDto storeUpdateDto, Long storeId);
    void deleteStore(Long storeId, Long userId, DeleteReasonType deleteReasonType);
}
