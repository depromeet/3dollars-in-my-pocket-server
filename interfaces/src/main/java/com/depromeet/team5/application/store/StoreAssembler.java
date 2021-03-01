package com.depromeet.team5.application.store;

import com.depromeet.team5.application.review.ReviewAssembler;
import com.depromeet.team5.application.user.UserAssembler;
import com.depromeet.team5.domain.Location;
import com.depromeet.team5.domain.review.Review;
import com.depromeet.team5.domain.store.AppearanceDay;
import com.depromeet.team5.domain.store.Image;
import com.depromeet.team5.domain.store.PaymentMethod;
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

import static com.depromeet.team5.util.LocationDistanceUtils.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class StoreAssembler {
    private final ReviewAssembler reviewAssembler;
    private final UserAssembler userAssembler;

    public StoreDetailDto toStoreDetailDto(Store store, Double latitude, Double longitude) {
        if (store == null) {
            return null;
        }
        StoreDetailDto storeDetailDto = new StoreDetailDto();
        storeDetailDto.setId(store.getId());
        storeDetailDto.setLatitude(store.getLatitude());
        storeDetailDto.setLongitude(store.getLongitude());
        storeDetailDto.setStoreName(store.getStoreName());
        storeDetailDto.setStoreType(store.getStoreType());
        storeDetailDto.setAppearanceDays(store.getAppearanceDays().stream().map(AppearanceDay::getDay).collect(Collectors.toSet()));
        storeDetailDto.setPaymentMethods(store.getPaymentMethods().stream().map(PaymentMethod::getMethod).collect(Collectors.toSet()));
        storeDetailDto.setCategory(store.getCategory());
        storeDetailDto.setCategories(store.getCategoryTypes());
        storeDetailDto.setImageResponses(this.toImageResponses(store.getImages()));
        storeDetailDto.setMenuResponses(store.getMenus().stream().map(MenuResponse::from).collect(Collectors.toList()));
        storeDetailDto.setReviewDetailResponses(store.getReviews().stream()
                .filter(Review::isVisible)
                .map(reviewAssembler::toReviewDetailResponse)
                .sorted(Comparator.comparing(ReviewDetailResponse::getCreatedAt).reversed())
                .collect(Collectors.toList()));
        storeDetailDto.setRating(Optional.ofNullable(store.getRating()).orElseGet(() -> {
            log.error("'rating' must not be null. storeId: {}", store.getId());
            return 0f;
        }));
        storeDetailDto.setDistance(getDistance(store.getLocation(), Location.of(latitude, longitude)));
        storeDetailDto.setUser(store.getUser());
        return storeDetailDto;
    }

    /**
     * 입력받은 위치를 기준으로 가게와의 거리를 계산하고 dto 로 변환합니다.
     *
     * @param stores   가게 목록
     * @param location 기준 위치
     * @return 거리별로 구분된 dto
     */
    public StoresGroupByDistanceDto toCategoryDistanceDto(List<Store> stores, Location location) {
        List<StoreCardDto> storeCardDtos = stores.stream()
                .map(it -> this.toStoreCardDto(it, location))
                .collect(Collectors.toList());

        StoresGroupByDistanceDto storesGroupByDistanceDto = new StoresGroupByDistanceDto();
        storesGroupByDistanceDto.setStoreList50(storeCardDtos.stream()
                .filter(it -> it.getDistance() >= 0 && it.getDistance() < 50)
                .sorted(Comparator.comparing(StoreCardDto::getDistance))
                .collect(Collectors.toList()));
        storesGroupByDistanceDto.setStoreList100(storeCardDtos.stream()
                .filter(it -> it.getDistance() >= 50 && it.getDistance() < 100)
                .sorted(Comparator.comparing(StoreCardDto::getDistance))
                .collect(Collectors.toList()));
        storesGroupByDistanceDto.setStoreList500(storeCardDtos.stream()
                .filter(it -> it.getDistance() >= 100 && it.getDistance() < 500)
                .sorted(Comparator.comparing(StoreCardDto::getDistance))
                .collect(Collectors.toList()));
        storesGroupByDistanceDto.setStoreList1000(storeCardDtos.stream()
                .filter(it -> it.getDistance() >= 500 && it.getDistance() < 1000)
                .sorted(Comparator.comparing(StoreCardDto::getDistance))
                .collect(Collectors.toList()));
        storesGroupByDistanceDto.setStoreListOver1000(storeCardDtos.stream()
                .filter(it -> it.getDistance() >= 1000)
                .sorted(Comparator.comparing(StoreCardDto::getDistance))
                .collect(Collectors.toList()));
        return storesGroupByDistanceDto;
    }

    /**
     * 입력받은 위치를 기준으로 가게와의 거리를 계산하고 dto 로 변환합니다.
     *
     * @param stores   가게 목록
     * @param location 기준 위치
     * @return 별점 별로 구분된 dto
     */
    public StoresGroupByRatingDto toCategoryReviewDto(List<Store> stores, Location location) {
        List<StoreCardDto> storeCardDtos = stores.stream()
                .map(it -> this.toStoreCardDto(it, location))
                .collect(Collectors.toList());

        StoresGroupByRatingDto storesGroupByRatingDto = new StoresGroupByRatingDto();
        storesGroupByRatingDto.setStoreList0(storeCardDtos.stream()
                .filter(it -> it.getRating() >= 0.0f && it.getRating() < 1.0f)
                .sorted(Comparator.comparing(StoreCardDto::getRating).reversed())
                .collect(Collectors.toList()));
        storesGroupByRatingDto.setStoreList1(storeCardDtos.stream()
                .filter(it -> it.getRating() >= 1.0f && it.getRating() < 2.0f)
                .sorted(Comparator.comparing(StoreCardDto::getRating).reversed())
                .collect(Collectors.toList()));
        storesGroupByRatingDto.setStoreList2(storeCardDtos.stream()
                .filter(it -> it.getRating() >= 2.0f && it.getRating() < 3.0f)
                .sorted(Comparator.comparing(StoreCardDto::getRating).reversed())
                .collect(Collectors.toList()));
        storesGroupByRatingDto.setStoreList3(storeCardDtos.stream()
                .filter(it -> it.getRating() >= 3.0f && it.getRating() < 4.0f)
                .sorted(Comparator.comparing(StoreCardDto::getRating).reversed())
                .collect(Collectors.toList()));
        storesGroupByRatingDto.setStoreList4(storeCardDtos.stream()
                .filter(it -> it.getRating() >= 4.0f && it.getRating() <= 5.0f)
                .sorted(Comparator.comparing(StoreCardDto::getRating).reversed())
                .collect(Collectors.toList()));
        return storesGroupByRatingDto;
    }

    public StoreCardDto toStoreCardDto(Store store, Location location) {
        if (store == null) {
            return null;
        }
        return StoreCardDto.of(store, location);
    }

    public StoreResponse toStoreResponse(Store store, Location location) {
        if (store == null) {
            return null;
        }
        StoreResponse storeResponse = new StoreResponse();
        storeResponse.setId(store.getId());
        storeResponse.setUserResponse(userAssembler.toUserResponse(store.getUser()));
        storeResponse.setStoreName(store.getStoreName());
        storeResponse.setCategory(store.getCategory().name());
        storeResponse.setCategories(store.getCategoryTypes().stream().map(Enum::toString).collect(Collectors.toList()));
        storeResponse.setRating(store.getRating());
<<<<<<< HEAD
        storeResponse.setDistance(LocationDistanceUtils.getDistance(store.getLocation(), location));
=======
        storeResponse.setDistance((int) LocationDistanceUtils.getDistance(store.getLatitude(), store.getLongitude(), location.getLatitude(), location.getLongitude(), "meter"));
>>>>>>> Squashed commit of the following:
        storeResponse.setImages(store.getImages());
        storeResponse.setLatitude(store.getLatitude());
        storeResponse.setLongitude(store.getLongitude());
        storeResponse.setCreatedAt(store.getCreatedAt());
        storeResponse.setUpdatedAt(store.getUpdatedAt());
        return storeResponse;
    }

    public List<ImageResponse> toImageResponses(List<Image> images) {
        return images.stream()
                .map(this::toImageResponse)
                .sorted(Comparator.comparing(ImageResponse::getId).reversed())
                .collect(Collectors.toList());
    }

    public ImageResponse toImageResponse(Image image) {
        if (image == null) {
            return null;
        }
        return ImageResponse.from(image);
    }
}
