package com.depromeet.team5.application.store;

import com.depromeet.team5.application.review.ReviewAssembler;
import com.depromeet.team5.domain.Location;
import com.depromeet.team5.domain.store.Store;
import com.depromeet.team5.dto.*;
import com.depromeet.team5.util.LocationDistanceUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
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

    /**
     * 입력받은 위치를 기준으로 가게와의 거리를 계산하고 dto 로 변환합니다.
     *
     * @param stores 가게 목록
     * @param location 기준 위치
     * @return 거리별로 구분된 dto
     */
    public CategoryDistanceDto toCategoryDistanceDto(List<Store> stores, Location location) {
        List<StoreCardDto> storeCardDtos = stores.stream()
                .map(it -> this.toStoreCardDto(it, location))
                .collect(Collectors.toList());

        CategoryDistanceDto categoryDistanceDto = new CategoryDistanceDto();
        categoryDistanceDto.setStoreList50(storeCardDtos.stream()
                .filter(it -> it.getDistance() < 50)
                .collect(Collectors.toList()));
        categoryDistanceDto.setStoreList100(storeCardDtos.stream()
                .filter(it -> it.getDistance() < 100)
                .collect(Collectors.toList()));
        categoryDistanceDto.setStoreList500(storeCardDtos.stream()
                .filter(it -> it.getDistance() < 500)
                .collect(Collectors.toList()));
        categoryDistanceDto.setStoreList1000(storeCardDtos.stream()
                .filter(it -> it.getDistance() < 1000)
                .collect(Collectors.toList()));
        categoryDistanceDto.setStoreListOver1000(storeCardDtos.stream()
                .filter(it -> it.getDistance() >= 1000)
                .collect(Collectors.toList()));
        return categoryDistanceDto;
    }

    public StoreCardDto toStoreCardDto(Store store, Location location) {
        if (store == null) {
            return null;
        }
        return StoreCardDto.of(store, location.getLatitude(), location.getLongitude());
    }
}
