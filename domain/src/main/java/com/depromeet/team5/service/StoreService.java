package com.depromeet.team5.service;

import com.depromeet.team5.domain.ImageUploadValue;
import com.depromeet.team5.domain.store.DeleteReasonType;
import com.depromeet.team5.domain.store.Store;
import com.depromeet.team5.domain.store.StoreCreateValue;
import com.depromeet.team5.domain.store.StoreUpdateValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StoreService {
    Store saveStore(StoreCreateValue storeCreateValue, Long userId, List<ImageUploadValue> imageUploadValues);

    List<Store> getAll(Double latitude, Double longitude);

    Page<Store> getAllByUser(Long userId, Pageable pageable);

    Store getStore(Long storeId);

    void updateStore(StoreUpdateValue storeUpdateValue, Long storeId, List<ImageUploadValue> imageUploadValues);

    void deleteStore(Long storeId, Long userId, DeleteReasonType deleteReasonType);
}
