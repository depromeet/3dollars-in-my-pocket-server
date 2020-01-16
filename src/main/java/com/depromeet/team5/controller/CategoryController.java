package com.depromeet.team5.controller;

import com.depromeet.team5.domain.CategoryTypes;
import com.depromeet.team5.dto.CategoryDto;
import com.depromeet.team5.service.CategoryService;
import com.depromeet.team5.util.auth.Auth;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @ApiOperation("거리순으로 특정 카테고리의 가게 정보를 가져옵니다. 인증이 필요한 요청입니다.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @Auth
    @GetMapping("/distance")
    public ResponseEntity<CategoryDto> getDistanceAll(@RequestParam Double latitude,
                                                      @RequestParam Double longitude,
                                                      @RequestParam CategoryTypes category) {
        return new ResponseEntity<>(categoryService.getDistanceList(latitude, longitude, category), HttpStatus.OK);
    }

    @ApiOperation("리뷰순으로 특정 카테고리의 가게 정보를 가져옵니다. 인증이 필요한 요청입니다.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @Auth
    @GetMapping("/review")
    public ResponseEntity<CategoryDto> getReviewAll(@RequestParam Double latitude,
                                                     @RequestParam Double longitude,
                                                     @RequestParam CategoryTypes category) {
        return new ResponseEntity<>(categoryService.getReviewList(latitude, longitude, category), HttpStatus.OK);
    }
}
