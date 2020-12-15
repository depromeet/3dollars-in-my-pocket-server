package com.depromeet.team5.application.store;

import com.depromeet.team5.application.review.ReviewAssembler;
import com.depromeet.team5.domain.store.Store;
import com.depromeet.team5.dto.ImageDto;
import com.depromeet.team5.dto.MenuDto;
import com.depromeet.team5.dto.StoreDetailDto;
import com.depromeet.team5.util.LocationDistanceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class StoreAssembler {
    private final ReviewAssembler reviewAssembler;

    public StoreDetailDto toStoreDetailDto(Store store, Double latitude, Double longitude) {
        if (store == null) {
            return null;
        }
        StoreDetailDto storeDetailDto = new StoreDetailDto();
        storeDetailDto.setId(store.getId());
        storeDetailDto.setLongitude(store.getLatitude());
        storeDetailDto.setLongitude(store.getLongitude());
        storeDetailDto.setStoreName(store.getStoreName());
        storeDetailDto.setCategory(store.getCategory());
        storeDetailDto.setImage(store.getImage().stream().map(ImageDto::from).collect(Collectors.toList()));
        storeDetailDto.setMenu(store.getMenu().stream().map(MenuDto::from).collect(Collectors.toList()));
        storeDetailDto.setReviewDetailResponses(store.getReview().stream()
                .map(reviewAssembler::toReviewDetailResponse)
                .collect(Collectors.toList()));
        storeDetailDto.setRating(store.getRating());
        storeDetailDto.setDistance((int) LocationDistanceUtils.getDistance(store.getLatitude(), store.getLongitude(), latitude, longitude, "meter"));
        storeDetailDto.setUser(store.getUser());
        return storeDetailDto;
    }
}
