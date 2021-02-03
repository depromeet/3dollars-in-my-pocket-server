package com.depromeet.team5.application.store;

import com.depromeet.team5.domain.ImageUploadValue;
import com.depromeet.team5.domain.Location;
import com.depromeet.team5.domain.store.CategoryType;
import com.depromeet.team5.domain.store.Image;
import com.depromeet.team5.domain.store.Store;
import com.depromeet.team5.dto.ImageResponse;
import com.depromeet.team5.dto.StoresGroupByDistanceDto;
import com.depromeet.team5.dto.StoresGroupByRatingDto;
import com.depromeet.team5.dto.StoreDetailDto;
import com.depromeet.team5.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Application Service
 * use-case 별로 메서드가 하나씩 존재합니다.
 * 로직은 여기에 직접 작성하지 않고, 상위 클래스들에게 위임해서 처리합니다.
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StoreApplicationService {
    private final StoreService storeService;
    private final StoreAssembler storeAssembler;

    public StoreDetailDto getStoreDetail(Long storeId, Double latitude, Double longitude) {
        Store store = storeService.getStore(storeId);
        return storeAssembler.toStoreDetailDto(store, latitude, longitude);
    }

    public StoresGroupByDistanceDto getStoresByCategoryGroupByDistance(
            CategoryType categoryType,
            Location userLocation,
            Location mapLocation
    ) {
        Assert.notNull(categoryType, "'categoryType' must not be null");
        Assert.notNull(userLocation, "'userLocation' must not be null");
        Assert.notNull(mapLocation, "'mapLocation' must not be null");

        List<Store> storeList = storeService.getStoresByDistanceBetweenAndCategory(
                mapLocation,
                0.0,
                1.0,
                categoryType
        );
        return storeAssembler.toCategoryDistanceDto(storeList, userLocation);
    }

    public StoresGroupByRatingDto getStoresByCategoryGroupByRating(
            CategoryType categoryType,
            Location userLocation,
            Location mapLocation
    ) {
        Assert.notNull(categoryType, "'categoryType' must not be null");
        Assert.notNull(userLocation, "'userLocation' must not be null");
        Assert.notNull(mapLocation, "'mapLocation' must not be null");

        List<Store> storeList = storeService.getStoresByDistanceBetweenAndCategory(
                mapLocation,
                0.0,
                1.0,
                categoryType
        );
        return storeAssembler.toCategoryReviewDto(storeList, userLocation);
    }

    @Transactional
    public List<ImageResponse> saveImages(Long storeId, List<ImageUploadValue> imageUploadValues) {
        List<Image> images = storeService.saveImages(storeId, imageUploadValues);
        return storeAssembler.toImageResponse(images);
    }

    public List<ImageResponse> getStoreImages(Long storeId) {
        List<Image> images = storeService.getStoreImages(storeId);
        return storeAssembler.toImageResponse(images);
    }
}
