package com.depromeet.team5.controller;

import com.depromeet.team5.application.store.StoreApplicationService;
import com.depromeet.team5.domain.ImageUploadValue;
import com.depromeet.team5.domain.store.*;
import com.depromeet.team5.dto.*;
import com.depromeet.team5.service.StoreService;
import com.depromeet.team5.application.security.Auth;
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

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Api(value = "Store")
@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/v1/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;
    private final StoreApplicationService storeApplicationService;

    @ApiOperation("가게 정보를 저장합니다. 인증이 필요한 요청입니다.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @Auth
    @PostMapping("/save")
    public ResponseEntity<StoreIdDto> save(StoreDto storeDto,
                                           @RequestPart(value = "image", required = false) List<MultipartFile> image,
                                           @RequestParam Long userId) {
        Store store = storeService.saveStore(
                StoreCreateValue.of(
                        storeDto.getLatitude(),
                        storeDto.getLongitude(),
                        storeDto.getStoreName(),
                        storeDto.getStoreType(),
                        Optional.ofNullable(storeDto.getAppearanceDays()).orElse(Collections.emptySet()),
                        Optional.ofNullable(storeDto.getPaymentMethods()).orElse(Collections.emptySet()),
                        storeDto.getCategory(),
                        Optional.ofNullable(storeDto.getMenu())
                                .map(menu -> menu.stream()
                                        .map(it -> MenuCreateValue.of(it.getName(), it.getPrice()))
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
        Pageable pageable = PageRequest.of(page - 1, 5, Sort.by("createdAt").descending());
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

    @ApiOperation("특정 가게의 정보를 수정합니다. 인증이 필요한 요청입니다.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @Auth
    @PutMapping("/update")
    public ResponseEntity<String> updateStore(StoreUpdateDto storeUpdateDto,
                                              @RequestPart(value = "image", required = false) List<MultipartFile> image,
                                              @RequestParam Long storeId) {
        storeService.updateStore(
                StoreUpdateValue.of(
                        storeUpdateDto.getLatitude(),
                        storeUpdateDto.getLongitude(),
                        storeUpdateDto.getStoreName(),
                        storeUpdateDto.getStoreType(),
                        Optional.ofNullable(storeUpdateDto.getAppearanceDays()).orElse(Collections.emptySet()),
                        Optional.ofNullable(storeUpdateDto.getPaymentMethods()).orElse(Collections.emptySet()),
                        Optional.ofNullable(storeUpdateDto.getMenu())
                                .map(menu -> menu.stream()
                                        .map(it -> MenuCreateValue.of(it.getName(), it.getPrice()))
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

    private ImageUploadValue toImageUploadValue(MultipartFile multipartFile) {
        try {
            return ImageUploadValue.of(multipartFile.getInputStream(), multipartFile.getOriginalFilename(), multipartFile.getContentType());
        } catch (IOException e) {
            log.error("Failed to read multipart File.", e);
            return null;
        }
    }
}
