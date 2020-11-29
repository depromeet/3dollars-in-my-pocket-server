package com.depromeet.team5.dto;

import com.depromeet.team5.domain.store.CategoryTypes;
import com.depromeet.team5.domain.store.Store;
import lombok.Data;

@Data
public class StoreMyPageDto {

    private Long id;

    private String storeName;

    private CategoryTypes category;

    private Float rating;

    public static StoreMyPageDto from(Store store) {
        StoreMyPageDto StoreMyPageDto = new StoreMyPageDto();
        StoreMyPageDto.id = store.getId();
        StoreMyPageDto.storeName = store.getStoreName();
        StoreMyPageDto.category = store.getCategory();
        StoreMyPageDto.rating = store.getRating();
        return StoreMyPageDto;
    }
}
