package com.depromeet.team5.dto;

import com.depromeet.team5.domain.CategoryTypes;
import com.depromeet.team5.domain.Store;
import com.depromeet.team5.util.LocationDistance;
import lombok.Data;

@Data
public class StoreCardDto {
    private Long id;
    private String storeName;
    private CategoryTypes category;
    private Long review;
    private Integer distance;
    private Double latitude;
    private Double longitude;

    public static StoreCardDto from(Store store) {
        StoreCardDto storeCardDto = new StoreCardDto();
        storeCardDto.id = store.getId();
        storeCardDto.storeName = store.getStoreName();
        storeCardDto.category = store.getCategory();
        storeCardDto.review = store.getReview();
        storeCardDto.distance = 0;
        storeCardDto.latitude = store.getLatitude();
        storeCardDto.longitude = store.getLongitude();
        return storeCardDto;
    }

    public static void calculationDistance(StoreCardDto storeCardDto, Double lat, Double lng) {
        LocationDistance locationDistance = new LocationDistance();
        storeCardDto.distance = (int)locationDistance.distance(storeCardDto.getLatitude(), storeCardDto.getLongitude(), lat, lng, "meter");
    }

}
