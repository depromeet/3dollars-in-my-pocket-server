package com.depromeet.team5.dto;

import com.depromeet.team5.domain.Location;
import com.depromeet.team5.domain.store.CategoryType;
import com.depromeet.team5.domain.store.Store;
import com.depromeet.team5.util.LocationDistanceUtils;
import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
public class StoreCardDto {

    private Long id;

    private String storeName;
    /**
     * 대표 카테고리
     */
    @Deprecated
    private CategoryType category;
    /**
     * 가게 카테고리 목록
     */
    private List<CategoryType> categories;

    private Integer distance;

    private Double latitude;

    private Double longitude;

    private Float rating;

    public static StoreCardDto of(Store store, Location userLocation) {
        StoreCardDto storeCardDto = new StoreCardDto();
        storeCardDto.id = store.getId();
        storeCardDto.storeName = store.getStoreName();
        storeCardDto.category = store.getCategory();
        storeCardDto.categories = store.getCategoryTypes();
        storeCardDto.distance = LocationDistanceUtils.getDistance(store.getLocation(), userLocation);
        storeCardDto.latitude = store.getLatitude();
        storeCardDto.longitude = store.getLongitude();
        storeCardDto.rating = Optional.ofNullable(store.getRating()).orElse(0f);
        return storeCardDto;
    }
}
