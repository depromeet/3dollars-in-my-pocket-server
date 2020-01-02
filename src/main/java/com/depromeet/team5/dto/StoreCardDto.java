package com.depromeet.team5.dto;

import com.depromeet.team5.domain.CategoryTypes;
import com.depromeet.team5.domain.Store;
import lombok.Data;

@Data
public class StoreCardDto {
    private Long id;
    private CategoryTypes category;
    private Long review;
    private Long distance;

    public static StoreCardDto from(Store store) {
        StoreCardDto storeCardDto = new StoreCardDto();
        storeCardDto.id = store.getId();
        storeCardDto.category = store.getCategory();
        storeCardDto.review = store.getReview();
        storeCardDto.distance = 0L;
        return storeCardDto;
    }
}
