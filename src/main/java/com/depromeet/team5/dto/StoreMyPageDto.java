package com.depromeet.team5.dto;

import com.depromeet.team5.domain.CategoryTypes;
import com.depromeet.team5.domain.Store;

public class StoreMyPageDto {
    private Long id;
    private String storeName;
    private CategoryTypes category;

    public static StoreMyPageDto from(Store store) {
        StoreMyPageDto storeMyPageDto = new StoreMyPageDto();
        storeMyPageDto.id = store.getId();
        storeMyPageDto.storeName = store.getStoreName();
        storeMyPageDto.category = store.getCategory();
        return storeMyPageDto;
    }
}
