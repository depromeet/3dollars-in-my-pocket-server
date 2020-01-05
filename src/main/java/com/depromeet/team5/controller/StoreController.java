package com.depromeet.team5.controller;

import com.depromeet.team5.domain.Store;
import com.depromeet.team5.dto.StoreCardDto;
import com.depromeet.team5.dto.StoreDto;
import com.depromeet.team5.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody StoreDto storeDto, @RequestParam Long userId) {
        storeService.saveStore(storeDto, userId);
        return new ResponseEntity<>("store save success", HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<List<StoreCardDto>> getAll(@RequestParam Float latitude, @RequestParam Float longitude, @RequestParam Float radius) {
        return new ResponseEntity<>(storeService.getAll(latitude, longitude), HttpStatus.OK);
    }

    @GetMapping("/get/detail")
    public ResponseEntity<Store> getDetail(@RequestParam Long storeId) {
        return new ResponseEntity<>(storeService.getDetail(storeId), HttpStatus.OK);
    }


    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam Long storeId) {
        storeService.deleteStore(storeId);
        return new ResponseEntity<>("store delete success", HttpStatus.OK);
    }
}
