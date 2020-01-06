package com.depromeet.team5.controller;

import com.depromeet.team5.domain.CategoryTypes;
import com.depromeet.team5.dto.StoreCardDto;
import com.depromeet.team5.service.CategoryService;
import com.depromeet.team5.util.auth.Auth;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Auth
    @GetMapping("/category")
    public ResponseEntity<List<StoreCardDto>> getAll(@RequestParam Float latitude,
                                                     @RequestParam Float longitude,
                                                     @RequestParam Float radius,
                                                     @RequestParam CategoryTypes category) {
        return new ResponseEntity<>(categoryService.getDistanceList(latitude, longitude, radius, category), HttpStatus.OK);
    }
}
