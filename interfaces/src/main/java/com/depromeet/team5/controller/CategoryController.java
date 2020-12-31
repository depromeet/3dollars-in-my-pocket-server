package com.depromeet.team5.controller;

import com.depromeet.team5.application.store.StoreApplicationService;
import com.depromeet.team5.domain.Location;
import com.depromeet.team5.domain.store.CategoryTypes;
import com.depromeet.team5.dto.StoresGroupByDistanceDto;
import com.depromeet.team5.dto.StoresGroupByRatingDto;
import com.depromeet.team5.application.security.Auth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "Category")
@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final StoreApplicationService storeApplicationService;

    @ApiOperation("거리순으로 특정 카테고리의 가게 정보를 가져옵니다. 인증이 필요한 요청입니다.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @Auth
    @GetMapping("/distance")
    public ResponseEntity<StoresGroupByDistanceDto> getStoresGroupByDistance(
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam(required = false) Double mapLatitude,
            @RequestParam(required = false) Double mapLongitude,
            @RequestParam CategoryTypes category
    ) {
        Location userLocation = Location.of(latitude, longitude);
        Location mapLocation = mapLatitude != null && mapLongitude != null
                ? Location.of(mapLatitude, mapLongitude)
                : userLocation;
        return ResponseEntity.ok(
                storeApplicationService.getStoresByCategoryGroupByDistance(
                        category,
                        userLocation,
                        mapLocation
                )
        );
    }

    @ApiOperation("리뷰순으로 특정 카테고리의 가게 정보를 가져옵니다. 인증이 필요한 요청입니다.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @Auth
    @GetMapping("/review")
    public ResponseEntity<StoresGroupByRatingDto> getStoresGroupByRating(
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam(required = false) Double mapLatitude,
            @RequestParam(required = false) Double mapLongitude,
            @RequestParam CategoryTypes category
    ) {
        Location userLocation = Location.of(latitude, longitude);
        Location mapLocation = mapLatitude != null && mapLongitude != null
                ? Location.of(mapLatitude, mapLongitude)
                : userLocation;

        return ResponseEntity.ok(
                storeApplicationService.getStoresByCategoryGroupByRating(
                        category,
                        userLocation,
                        mapLocation
                )
        );
    }
}
