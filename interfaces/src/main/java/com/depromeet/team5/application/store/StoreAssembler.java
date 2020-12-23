package com.depromeet.team5.application.store;

import com.depromeet.team5.application.review.ReviewAssembler;
import com.depromeet.team5.domain.review.Review;
import com.depromeet.team5.domain.store.Store;
import com.depromeet.team5.dto.ImageDto;
import com.depromeet.team5.dto.MenuDto;
import com.depromeet.team5.dto.ReviewDetailResponse;
import com.depromeet.team5.dto.StoreDetailDto;
import com.depromeet.team5.util.LocationDistanceUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
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
        storeDetailDto.setLatitude(store.getLatitude());
        storeDetailDto.setLongitude(store.getLongitude());
        storeDetailDto.setStoreName(store.getStoreName());
        storeDetailDto.setCategory(store.getCategory());
        storeDetailDto.setImage(store.getImage().stream().map(ImageDto::from).collect(Collectors.toList()));
        storeDetailDto.setMenu(store.getMenu().stream().map(MenuDto::from).collect(Collectors.toList()));
        storeDetailDto.setReviewDetailResponses(store.getReview().stream()
                .filter(Review::isVisible)
                .map(reviewAssembler::toReviewDetailResponse)
                .sorted(Comparator.comparing(ReviewDetailResponse::getCreatedAt).reversed())
                .collect(Collectors.toList()));
        storeDetailDto.setRating(Optional.ofNullable(store.getRating()).orElseGet(() -> {
           log.error("'rating' must not be null. storeId: {}", store.getId());
           return 0f;
        }));
        storeDetailDto.setDistance((int) LocationDistanceUtils.getDistance(store.getLatitude(), store.getLongitude(), latitude, longitude, "meter"));
        storeDetailDto.setUser(store.getUser());
        return storeDetailDto;
    }
}
