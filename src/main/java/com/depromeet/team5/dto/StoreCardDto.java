package com.depromeet.team5.dto;

import com.depromeet.team5.domain.CategoryTypes;
import com.depromeet.team5.domain.Store;
import com.depromeet.team5.util.LocationDistance;
import lombok.Data;

@Data
public class StoreCardDto {

    private Long id;
    private CategoryTypes category;
    private Long review;
    private Double distance;

    public static StoreCardDto from(Store store) {
        LocationDistance locationDistance = new LocationDistance();
        StoreCardDto storeCardDto = new StoreCardDto();
        storeCardDto.id = store.getId();
        storeCardDto.category = store.getCategory();
        storeCardDto.review = store.getReview();
        storeCardDto.distance = locationDistance.distance(store.getLatitude(), store.getLongitude(), store.getLatitude(), store.getLongitude(), "meter");
        return storeCardDto;
    }

}
