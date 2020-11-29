package com.depromeet.team5.controller;

import com.depromeet.team5.domain.Review;
import com.depromeet.team5.dto.ReviewDto;
import com.depromeet.team5.dto.ReviewPomDto;
import com.depromeet.team5.service.ReviewService;
import com.depromeet.team5.util.auth.Auth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "Review")
@CrossOrigin(origins = {"*"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/review")
public class ReviewController {

    private final ReviewService reviewService;

    @ApiOperation("리뷰를 등록합니다. 인증이 필요한 요청입니다.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @Auth
    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody ReviewDto reviewDto, @RequestParam Long userId, @RequestParam Long storeId) {
        reviewService.saveReview(reviewDto, userId, storeId);
        return new ResponseEntity<>("리뷰 등록에 성공했습니다.", HttpStatus.OK);
    }

    @ApiOperation("사용자가 작성한 리뷰를 조회합니다. 인증이 필요한 요청입니다.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @Auth
    @GetMapping("/user")
    public ResponseEntity<ReviewPomDto> getAllByUser(@RequestParam Long userId, @RequestParam Integer page) {
        Pageable pageable = PageRequest.of(page-1, 3, Sort.by("createdAt").descending());
        return new ResponseEntity<>(reviewService.getAllByUser(userId, pageable), HttpStatus.OK);
    }
}
