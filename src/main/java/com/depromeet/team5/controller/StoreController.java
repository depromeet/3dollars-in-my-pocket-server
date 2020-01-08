package com.depromeet.team5.controller;

import com.depromeet.team5.domain.Store;
import com.depromeet.team5.dto.StoreCardDto;
import com.depromeet.team5.dto.StoreDto;
import com.depromeet.team5.dto.UpdateDto;
import com.depromeet.team5.service.StoreService;
import com.depromeet.team5.util.auth.Auth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
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
    @Auth
    @PostMapping("/save")
    public ResponseEntity<String> save(StoreDto storeDto,
                                       @RequestPart(value = "image", required = false) List<MultipartFile> image,
                                       @RequestParam Long userId) {
        if (image != null) storeDto.setImage(image);
        storeService.saveStore(storeDto, userId);
        return new ResponseEntity<>("store save success", HttpStatus.OK);
    }

    @ApiOperation("모든 가게의 정보를 조회합니다. 인증이 필요한 요청입니다.")
    @Auth
    @GetMapping("/get")
    public ResponseEntity<List<StoreCardDto>> getAll(@RequestParam Float latitude,
                                                     @RequestParam Float longitude) {
        return new ResponseEntity<>(storeService.getAll(latitude, longitude), HttpStatus.OK);
    }

    @ApiOperation("특정 가게의 정보를 조회합니다. 인증이 필요한 요청입니다.")
    @Auth
    @GetMapping("/detail")
    public ResponseEntity<Store> getDetail(@RequestParam Long storeId) {
        return new ResponseEntity<>(storeService.getDetail(storeId), HttpStatus.OK);
    }

    @ApiOperation("특정 가게의 정보를 수정합니다. 인증이 필요한 요청입니다.")
    @Auth
    @PutMapping("/update")
    public ResponseEntity<String> getUpdate(UpdateDto updateDto,
                                            @RequestPart(value = "image", required = false) List<MultipartFile> image,
                                            @RequestParam Long storeId) {
        if (image != null) updateDto.setImage(image);
        storeService.updateStore(updateDto, storeId);
        return new ResponseEntity<>("store update success", HttpStatus.OK);
    }

    @ApiOperation("특정 가게의 정보를 삭제합니다. 인증이 필요한 요청입니다.")
    @Auth
    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam Long storeId) {
        storeService.deleteStore(storeId);
        return new ResponseEntity<>("store delete success", HttpStatus.OK);
    }
}
