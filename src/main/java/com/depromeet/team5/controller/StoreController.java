package com.depromeet.team5.controller;

import com.depromeet.team5.domain.Store;
import com.depromeet.team5.dto.StoreCardDto;
import com.depromeet.team5.dto.StoreDto;
import com.depromeet.team5.dto.UpdateDto;
import com.depromeet.team5.service.StoreService;
import com.depromeet.team5.util.auth.Auth;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @Auth
    @PostMapping("/save")
    public ResponseEntity<String> save(StoreDto storeDto,
                                       @RequestPart(value = "image", required = false) List<MultipartFile> image,
                                       @RequestParam Long userId) {
        if (image != null) storeDto.setImage(image);
        storeService.saveStore(storeDto, userId);
        return new ResponseEntity<>("store save success", HttpStatus.OK);
    }

    @Auth
    @GetMapping("/get")
    public ResponseEntity<List<StoreCardDto>> getAll(@RequestParam Float latitude,
                                                     @RequestParam Float longitude) {
        return new ResponseEntity<>(storeService.getAll(latitude, longitude), HttpStatus.OK);
    }

    @Auth
    @GetMapping("/detail")
    public ResponseEntity<Store> getDetail(@RequestParam Long storeId) {
        return new ResponseEntity<>(storeService.getDetail(storeId), HttpStatus.OK);
    }

    @Auth
    @PutMapping("/update")
    public ResponseEntity<String> getUpdate(UpdateDto updateDto,
                                            @RequestPart(value = "image", required = false) List<MultipartFile> image,
                                            @RequestParam Long storeId) {
        if (image != null) updateDto.setImage(image);
        storeService.updateStore(updateDto, storeId);
        return new ResponseEntity<>("store update success", HttpStatus.OK);
    }

    @Auth
    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam Long storeId,
                                         @RequestParam Long userId) {
        storeService.deleteStore(storeId, userId);
        return new ResponseEntity<>("store delete success", HttpStatus.OK);
    }

}
