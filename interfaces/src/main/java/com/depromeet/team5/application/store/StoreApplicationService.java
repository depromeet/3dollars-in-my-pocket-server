package com.depromeet.team5.application.store;

import com.depromeet.team5.domain.ImageUploadValue;
import com.depromeet.team5.domain.Location;
import com.depromeet.team5.domain.store.CategoryType;
import com.depromeet.team5.domain.store.Image;
import com.depromeet.team5.domain.store.Store;
import com.depromeet.team5.dto.*;
import com.depromeet.team5.service.S3FileUploadService;
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
@RequiredArgsConstructor
public class StoreApplicationService {

    private final StoreService storeService;
    private final StoreAssembler storeAssembler;
    private final S3FileUploadService s3FileUploadService;

    private static final double DISTANCE_LIMIT = 2.0;

    @Transactional(readOnly = true)
    public StoreDetailDto getStoreDetail(Long storeId, Double latitude, Double longitude) {
        Store store = storeService.getStore(storeId);
        return storeAssembler.toStoreDetailDto(store, latitude, longitude);
    }

    /**
     * 해당 서비스 메소드가 내부적으로 사용하는 쿼리의 성능 이슈가 존재하여
     * 최대 검색 반경을 2km로 제한합니다.
     * 추후 쿼리 최적화 혹은 새 API 개발을 통해 고도화할 예정입니다.
     */
    @Transactional(readOnly = true)
    public List<StoreResponse> getStoresByLocationAndDistance(Location userLocation,
                                                              Location mapLocation,
                                                              Double distance
    ) {
        Assert.notNull(userLocation, "'userLocation' must not be null");
        Assert.notNull(mapLocation, "'mapLocation' must not be null");
        Assert.notNull(distance, "'distance' must not be null");

        if (distance > DISTANCE_LIMIT) distance = DISTANCE_LIMIT;

        return storeService.getStoresByLocationAndDistance(mapLocation, distance).stream()
                .map(it -> storeAssembler.toStoreResponse(it, userLocation))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    public List<ImageResponse> getStoreImages(Long storeId) {
        Assert.notNull(storeId, "'storeId' must not be null");

        List<Image> images = storeService.getStoreImages(storeId);
        return storeAssembler.toImageResponses(images);
    }

    /**
     * 가게에 이미지 1개 추가
     *
     * @param storeId          가게 식별자
     * @param imageUploadValue 이미지 업로드 정보
     * @return 가게에 추가된 이미지 정보
     */
    public ImageResponse addImage(Long storeId, ImageUploadValue imageUploadValue) {
        Assert.notNull(storeId, "'storeId' must not be null");
        Assert.notNull(imageUploadValue, "'imageUploadValue' must not be null");

        String imageUrl = s3FileUploadService.upload(imageUploadValue);
        Image image = storeService.addImage(storeId, imageUrl);
        return storeAssembler.toImageResponse(image);
    }
}
