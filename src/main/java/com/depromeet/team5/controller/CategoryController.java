package com.depromeet.team5.controller;

import com.depromeet.team5.domain.CategoryTypes;
import com.depromeet.team5.dto.CategoryDto;
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
    @GetMapping("/category/distance")
    public ResponseEntity<CategoryDto> getDistanceAll(@RequestParam Float latitude,
                                                      @RequestParam Float longitude,
                                                      @RequestParam CategoryTypes category) {
        return new ResponseEntity<>(categoryService.getDistanceList(latitude, longitude, category), HttpStatus.OK);
    }

    @Auth
    @GetMapping("/category/review")
    public ResponseEntity<CategoryDto> getReviewAll(@RequestParam Float latitude,
                                                     @RequestParam Float longitude,
                                                     @RequestParam CategoryTypes category) {
        return new ResponseEntity<>(categoryService.getReviewList(latitude, longitude, category), HttpStatus.OK);
    }
}
