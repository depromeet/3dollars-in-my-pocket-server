package com.depromeet.team5.dto;

import com.depromeet.team5.domain.store.CategoryType;
import com.depromeet.team5.domain.store.Store;
import com.depromeet.team5.util.LocationDistanceUtils;
import lombok.Data;

@Data
public class StoreCardDto {

    private Long id;

    private String storeName;

    private CategoryType category;

    private Integer distance;

    private Double latitude;

    private Double longitude;

    private Float rating;

    public static StoreCardDto of(Store store, Double latitude, Double longitude) {
        StoreCardDto storeCardDto = new StoreCardDto();
        storeCardDto.id = store.getId();
        storeCardDto.storeName = store.getStoreName();
        storeCardDto.category = store.getCategory();
        storeCardDto.distance = (int) LocationDistanceUtils.getDistance(store.getLatitude(), store.getLongitude(), latitude, longitude, "meter");
        storeCardDto.latitude = store.getLatitude();
        storeCardDto.longitude = store.getLongitude();
        storeCardDto.rating = store.getRating();
        return storeCardDto;
    }
}
