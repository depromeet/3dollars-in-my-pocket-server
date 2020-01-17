package com.depromeet.team5.dto;

import com.depromeet.team5.domain.CategoryTypes;
import com.depromeet.team5.domain.Store;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class StoreDetailDto {

    private Long id;

    private Double latitude;

    private Double longitude;

    private String storeName;

    private CategoryTypes category;

    private List<ImageDto> image;

    private List<MenuDto> menu = new ArrayList<>();

    private List<ReviewDto> review = new ArrayList<>();

    private Float rating;

    public static StoreDetailDto from(Store store) {
        StoreDetailDto storeDetailDto = new StoreDetailDto();
        storeDetailDto.id = store.getId();
        storeDetailDto.latitude = store.getLatitude();
        storeDetailDto.longitude = store.getLongitude();
        storeDetailDto.storeName = store.getStoreName();
        storeDetailDto.category = store.getCategory();
        storeDetailDto.image = store.getImage().stream().map(ImageDto::from).collect(Collectors.toList());
        storeDetailDto.menu = store.getMenu().stream().map(MenuDto::from).collect(Collectors.toList());
        storeDetailDto.review = store.getReview().stream().map(ReviewDto::from).collect(Collectors.toList());
        storeDetailDto.rating = store.getRating();
        return storeDetailDto;
    }
}
