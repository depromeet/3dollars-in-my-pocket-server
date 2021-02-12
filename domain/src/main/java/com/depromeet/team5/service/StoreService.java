package com.depromeet.team5.service;

import com.amazonaws.services.kms.model.CustomKeyStoresListEntry;
import com.depromeet.team5.domain.ImageUploadValue;
import com.depromeet.team5.domain.Location;
import com.depromeet.team5.domain.store.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StoreService {
    Store saveStore(StoreCreateValue storeCreateValue, Long userId, List<ImageUploadValue> imageUploadValues);

    List<Store> getStoresByDistanceBetweenAndCategory(Location location, Double distanceStart, Double distanceEnd, CategoryType categoryType);

    List<Store> getAll(Double latitude, Double longitude);

    List<Store> getStoresByLocationAndDistance(Location location, Double distance);

    Page<Store> getAllByUser(Long userId, Pageable pageable);

    Store getStore(Long storeId);

    void updateStore(StoreUpdateValue storeUpdateValue, Long storeId, List<ImageUploadValue> imageUploadValues);

    void deleteStore(Long storeId, Long userId, DeleteReasonType deleteReasonType);

    Image addImage(Long storeId, String imageUrl);

    void deleteImage(Long imageId);

    List<Image> getStoreImages(Long storeId);
}
