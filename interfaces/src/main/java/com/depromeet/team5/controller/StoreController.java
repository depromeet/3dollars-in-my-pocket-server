package com.depromeet.team5.controller;

import com.depromeet.team5.application.security.Auth;
import com.depromeet.team5.application.store.StoreApplicationService;
import com.depromeet.team5.domain.ImageUploadValue;
import com.depromeet.team5.domain.Location;
import com.depromeet.team5.domain.store.*;
import com.depromeet.team5.dto.*;
import com.depromeet.team5.service.StoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Api(value = "Store")
@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping({"/api/v1/store", "/api/v1/stores"})
@RequiredArgsConstructor
public class StoreController {

    private static final int PAGE_SIZE = 20;

    private final StoreService storeService;
    private final StoreApplicationService storeApplicationService;

    @ApiOperation("가게 정보를 저장합니다. 인증이 필요한 요청입니다.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @Auth
    @PostMapping("/save")
    public ResponseEntity<StoreIdDto> save(@Valid StoreDto storeDto,
                                           @RequestPart(value = "image", required = false) List<MultipartFile> image,
                                           @RequestParam Long userId) {
        Store store = storeService.saveStore(
                StoreCreateValue.of(
                        storeDto.getLatitude(),
                        storeDto.getLongitude(),
                        storeDto.getStoreName(),
                        storeDto.getCategory(),
                        storeDto.getCategories() != null ? storeDto.getCategories() : Collections.emptyList(),
                        storeDto.getStoreType(),
                        Optional.ofNullable(storeDto.getAppearanceDays()).orElse(Collections.emptySet()),
                        Optional.ofNullable(storeDto.getPaymentMethods()).orElse(Collections.emptySet()),
                        Optional.ofNullable(storeDto.getMenu())
                                .map(menu -> menu.stream()
                                        .map(it -> MenuCreateValue.of(
                                                it.getCategory() != null ? it.getCategory() : storeDto.getCategory(),
                                                it.getName(),
                                                it.getPrice()))
                                        .collect(Collectors.toList())
                                ).orElse(Collections.emptyList())
                ),
                userId,
                Optional.ofNullable(image)
                        .map(images -> images.stream()
                                .map(this::toImageUploadValue)
                                .collect(Collectors.toList())
                        ).orElse(Collections.emptyList())
        );
        StoreIdDto storeIdDto = new StoreIdDto();
        storeIdDto.setStoreId(store.getId());
        return ResponseEntity.ok(storeIdDto);
    }

    @Deprecated
    @ApiOperation("내 주변 가게들을 조회합니다(거리 가까운순 5개). 인증이 필요한 요청입니다.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @Auth
    @GetMapping("/get")
    public ResponseEntity<List<StoreCardDto>> getAll(@RequestParam Double latitude,
                                                     @RequestParam Double longitude) {
        return ResponseEntity.ok(
                storeService.getAll(latitude, longitude)
                        .stream()
                        .map(store -> StoreCardDto.of(store, latitude, longitude))
                        .collect(Collectors.toList())
        );
    }

    @ApiOperation("사용자가 작성한 가게의 정보를 조회합니다. 인증이 필요한 요청입니다.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @Auth
    @GetMapping("/user")
    public ResponseEntity<StoreMyPagePomDto> getAllByUser(@RequestParam Long userId,
                                                          @RequestParam Integer page) {
        Pageable pageable = PageRequest.of(page - 1, PAGE_SIZE, Sort.by("createdAt").descending());
        Page<Store> storePage = storeService.getAllByUser(userId, pageable);
        List<StoreMyPageDto> storeMyPageList = storePage
                .getContent()
                .stream()
                .map(StoreMyPageDto::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(
                StoreMyPagePomDto.from(
                        storeMyPageList,
                        storePage.getTotalElements(),
                        storePage.getTotalPages()
                )
        );
    }

    @ApiOperation("특정 가게의 정보를 조회합니다. 인증이 필요한 요청입니다.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @Auth
    @GetMapping("/detail")
    public ResponseEntity<StoreDetailDto> getDetail(@RequestParam Long storeId,
                                                    @RequestParam Double latitude,
                                                    @RequestParam Double longitude) {
        return ResponseEntity.ok(storeApplicationService.getStoreDetail(storeId, latitude, longitude));
    }

    @ApiOperation("위도, 경도 주위 가게 목록을 조회합니다. 인증이 필요한 요청입니다.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @Auth
    @GetMapping
    public ResponseEntity<List<StoreResponse>> getStoresByLocation(@RequestParam Double latitude,
                                                                   @RequestParam Double longitude,
                                                                   @RequestParam Double mapLatitude,
                                                                   @RequestParam Double mapLongitude,
                                                                   @RequestParam Double distance) {
        return ResponseEntity.ok(
                storeApplicationService.getStoresByLocationAndDistance(
                        Location.of(latitude, longitude),
                        Location.of(mapLatitude, mapLongitude),
                        distance / 1000
                )
        );
    }

    @ApiOperation("특정 가게의 정보를 수정합니다. 인증이 필요한 요청입니다.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @Auth
    @PutMapping("/update")
    public ResponseEntity<String> updateStore(@Valid StoreUpdateDto storeUpdateDto,
                                              @RequestPart(value = "image", required = false) List<MultipartFile> image,
                                              @RequestParam Long storeId) {
        storeService.updateStore(
                StoreUpdateValue.of(
                        storeUpdateDto.getLatitude(),
                        storeUpdateDto.getLongitude(),
                        storeUpdateDto.getStoreName(),
                        storeUpdateDto.getCategory(),
                        storeUpdateDto.getCategories() != null ? storeUpdateDto.getCategories() : Collections.emptyList(),
                        storeUpdateDto.getStoreType(),
                        Optional.ofNullable(storeUpdateDto.getAppearanceDays()).orElse(Collections.emptySet()),
                        Optional.ofNullable(storeUpdateDto.getPaymentMethods()).orElse(Collections.emptySet()),
                        Optional.ofNullable(storeUpdateDto.getMenu())
                                .map(menu -> menu.stream()
                                        .map(it -> MenuCreateValue.of(
                                                it.getCategory() != null ? it.getCategory() : storeService.getStore(storeId).getCategory(),
                                                it.getName(),
                                                it.getPrice()))
                                        .collect(Collectors.toList()))
                                .orElse(Collections.emptyList())
                ),
                storeId,
                Optional.ofNullable(image)
                        .map(it -> it.stream()
                                .map(this::toImageUploadValue)
                                .collect(Collectors.toList()))
                        .orElse(Collections.emptyList())
        );
        return new ResponseEntity<>("store update success", HttpStatus.OK);
    }

    @ApiOperation("특정 가게의 정보를 삭제합니다. 인증이 필요한 요청입니다.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @Auth
    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam Long storeId,
                                         @RequestParam Long userId,
                                         @RequestParam DeleteReasonType deleteReasonType) {
        storeService.deleteStore(storeId, userId, deleteReasonType);
        return new ResponseEntity<>("store delete success", HttpStatus.OK);
    }

    @ApiOperation("가게의 이미지를 등록합니다. 인증이 필요한 요청입니다.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @Auth
    @PostMapping("/{storeId}/images")
    public ResponseEntity<ImageResponse> addImage(@PathVariable Long storeId,
                                                  @RequestPart(value = "image") MultipartFile multipartFile) {
        ImageUploadValue imageUploadValue = this.toImageUploadValue(multipartFile);
        ImageResponse imageResponse = storeApplicationService.addImage(storeId, imageUploadValue);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(imageResponse.getId())
                .toUri();
        return ResponseEntity.created(location).body(imageResponse);
    }

    @ApiOperation("가게의 이미지를 삭제합니다. 인증이 필요한 요청입니다.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @Auth
    @DeleteMapping("{storeId}/images/{imageId}")
    public ResponseEntity<String> deleteImage(@PathVariable Long storeId,
                                              @PathVariable Long imageId) {
        storeService.deleteImage(imageId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation("특정 가게의 이미지를 조회합니다. 인증이 필요한 요청입니다. 생성일 역순으로 정렬")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @Auth
    @GetMapping("/{storeId}/images")
    public ResponseEntity<List<ImageResponse>> getStoreImages(@PathVariable Long storeId) {
        return ResponseEntity.ok(storeApplicationService.getStoreImages(storeId));
    }

    private ImageUploadValue toImageUploadValue(MultipartFile multipartFile) {
        try {
            return ImageUploadValue.of(multipartFile.getInputStream(), multipartFile.getOriginalFilename(), multipartFile.getContentType());
        } catch (IOException e) {
            log.error("Failed to read multipart File.", e);
            return null;
        }
    }
}
