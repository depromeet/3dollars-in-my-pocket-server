package com.depromeet.team5.controller;

import com.depromeet.team5.domain.DeleteReasonType;
import com.depromeet.team5.dto.*;
import com.depromeet.team5.service.StoreService;
import com.depromeet.team5.util.auth.Auth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(value = "Store")
@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/v1/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @ApiOperation("가게 정보를 저장합니다. 인증이 필요한 요청입니다.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @Auth
    @PostMapping("/save")
    public ResponseEntity<StoreIdDto> save(StoreDto storeDto,
                                       @RequestPart(value = "image", required = false) List<MultipartFile> image,
                                       @RequestParam Long userId) {
        if (image != null) storeDto.setImage(image);
        return new ResponseEntity<>(storeService.saveStore(storeDto, userId), HttpStatus.OK);
    }

    @ApiOperation("모든 가게의 정보를 조회합니다. 인증이 필요한 요청입니다.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @Auth
    @GetMapping("/get")
    public ResponseEntity<StoreCardPomDto> getAll(@RequestParam Double latitude,
                                                     @RequestParam Double longitude,
                                                     @RequestParam Integer page) {
        Pageable pageable = PageRequest.of(page-1, 5, Sort.by("createdAt").descending());
        return new ResponseEntity<>(storeService.getAll(latitude, longitude, pageable), HttpStatus.OK);
    }

    @ApiOperation("사용자가 작성한 가게의 정보를 조회합니다. 인증이 필요한 요청입니다.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @Auth
    @GetMapping("/user")
    public ResponseEntity<StoreMyPagePomDto> getAllByUser(@RequestParam Long userId,
                                                    @RequestParam Integer page) {
        Pageable pageable = PageRequest.of(page-1, 5, Sort.by("createdAt").descending());
        return new ResponseEntity<>(storeService.getAllByUser(userId, pageable), HttpStatus.OK);
    }

    @ApiOperation("특정 가게의 정보를 조회합니다. 인증이 필요한 요청입니다.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @Auth
    @GetMapping("/detail")
    public ResponseEntity<StoreDetailDto> getDetail(@RequestParam Long storeId,
                                                    @RequestParam Double latitude,
                                                    @RequestParam Double longitude) {
        return new ResponseEntity<>(storeService.getDetail(storeId, latitude, longitude), HttpStatus.OK);
    }

    @ApiOperation("특정 가게의 정보를 수정합니다. 인증이 필요한 요청입니다.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @Auth
    @PutMapping("/update")
    public ResponseEntity<String> getUpdate(StoreUpdateDto storeUpdateDto,
                                            @RequestPart(value = "image", required = false) List<MultipartFile> image,
                                            @RequestParam Long storeId) {
        if (image != null) storeUpdateDto.setImage(image);
        storeService.updateStore(storeUpdateDto, storeId);
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
}
