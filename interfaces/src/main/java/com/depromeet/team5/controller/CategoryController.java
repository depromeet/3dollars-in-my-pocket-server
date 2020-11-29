package com.depromeet.team5.controller;

import com.depromeet.team5.domain.CategoryTypes;
import com.depromeet.team5.dto.CategoryDistanceDto;
import com.depromeet.team5.dto.CategoryReviewDto;
import com.depromeet.team5.service.CategoryService;
import com.depromeet.team5.util.auth.Auth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "Category")
@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @ApiOperation("거리순으로 특정 카테고리의 가게 정보를 가져옵니다. 인증이 필요한 요청입니다.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @Auth
    @GetMapping("/distance")
    public ResponseEntity<CategoryDistanceDto> getDistanceAll(@RequestParam Double latitude,
                                                              @RequestParam Double longitude,
                                                              @RequestParam CategoryTypes category) {
        return new ResponseEntity<>(categoryService.getDistanceList(latitude, longitude, category), HttpStatus.OK);
    }

    @ApiOperation("리뷰순으로 특정 카테고리의 가게 정보를 가져옵니다. 인증이 필요한 요청입니다.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @Auth
    @GetMapping("/review")
    public ResponseEntity<CategoryReviewDto> getReviewAll(@RequestParam Double latitude,
                                                          @RequestParam Double longitude,
                                                          @RequestParam CategoryTypes category) {
        return new ResponseEntity<>(categoryService.getReviewList(latitude, longitude, category), HttpStatus.OK);
    }
}
