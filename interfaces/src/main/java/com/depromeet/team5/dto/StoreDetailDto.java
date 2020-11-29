package com.depromeet.team5.dto;

import com.depromeet.team5.domain.store.CategoryTypes;
import com.depromeet.team5.domain.store.Review;
import com.depromeet.team5.domain.store.Store;
import com.depromeet.team5.domain.user.User;
import com.depromeet.team5.util.LocationDistance;
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

    private List<Review> review = new ArrayList<>();

    private Float rating;

    private Integer distance;

    private User user;

    public static StoreDetailDto from(Store store) {
        StoreDetailDto storeDetailDto = new StoreDetailDto();
        storeDetailDto.id = store.getId();
        storeDetailDto.latitude = store.getLatitude();
        storeDetailDto.longitude = store.getLongitude();
        storeDetailDto.storeName = store.getStoreName();
        storeDetailDto.category = store.getCategory();
        storeDetailDto.image = store.getImage().stream().map(ImageDto::from).collect(Collectors.toList());
        storeDetailDto.menu = store.getMenu().stream().map(MenuDto::from).collect(Collectors.toList());
        storeDetailDto.review = store.getReview();
        storeDetailDto.rating = store.getRating();
        storeDetailDto.user = store.getUser();
        return storeDetailDto;
    }

    public static void calculationDistance(StoreDetailDto storeDetailDto, Double lat, Double lng) {
        LocationDistance locationDistance = new LocationDistance();
        storeDetailDto.distance = (int)locationDistance.distance(storeDetailDto.getLatitude(), storeDetailDto.getLongitude(), lat, lng, "meter");
    }
}
