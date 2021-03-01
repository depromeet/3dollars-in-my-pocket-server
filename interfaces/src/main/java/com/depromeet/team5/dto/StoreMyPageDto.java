package com.depromeet.team5.dto;

import com.depromeet.team5.domain.Location;
import com.depromeet.team5.domain.store.CategoryType;
import com.depromeet.team5.domain.store.Store;
import com.depromeet.team5.util.LocationDistanceUtils;
import lombok.Data;

import java.util.List;

@Data
public class StoreMyPageDto {

    private Long id;

    private String storeName;

    private CategoryType category;

    /**
     * 카테고리 목록
     */
    private List<CategoryType> categories;

    private Float rating;

    /**
     * 현위치로부터 가게 거리
     */
    private Integer distance;

    public static StoreMyPageDto of(Store store, Location userLocation) {
        StoreMyPageDto storeMyPageDto = new StoreMyPageDto();
        storeMyPageDto.id = store.getId();
        storeMyPageDto.storeName = store.getStoreName();
        storeMyPageDto.category = store.getCategory();
        storeMyPageDto.categories = store.getCategoryTypes();
        storeMyPageDto.rating = store.getRating();
        storeMyPageDto.distance = LocationDistanceUtils.getDistance(userLocation, store.getLocation());
        return storeMyPageDto;
    }
}
