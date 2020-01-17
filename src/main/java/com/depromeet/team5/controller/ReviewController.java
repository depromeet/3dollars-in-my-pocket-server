package com.depromeet.team5.controller;

import com.depromeet.team5.dto.ReviewDto;
import com.depromeet.team5.dto.ReviewUpdateDto;
import com.depromeet.team5.service.ReviewService;
import com.depromeet.team5.util.auth.Auth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<String> save(@RequestBody ReviewDto reviewDto, @RequestParam Long userId, @RequestParam  Long storeId) {
        reviewService.saveReview(reviewDto, userId, storeId);
        return new ResponseEntity<>("리뷰 등록에 성공했습니다.", HttpStatus.OK);
    }

    @ApiOperation("사용자가 작성한 리뷰를 조회합니다. 인증이 필요한 요청입니다.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @Auth
    @GetMapping("/user")
    public ResponseEntity<List<ReviewDto>> getAllByUser(@RequestParam Long userId) {
        return new ResponseEntity<>(reviewService.getAllByUser(userId), HttpStatus.OK);
    }

    @ApiOperation("리뷰를 수정합니다. 인증이 필요한 요청입니다.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @Auth
    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody ReviewUpdateDto reviewUpdateDto, @RequestParam Long reviewId) {
        reviewService.updateReview(reviewUpdateDto, reviewId);
        return new ResponseEntity<>("리뷰 수정에 성공했습니다.", HttpStatus.OK);
    }

    @ApiOperation("리뷰를 삭제합니다. 인증이 필요한 요청입니다.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @Auth
    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam Long reviewId) {
        reviewService.deleteReview(reviewId);
        return new ResponseEntity<>("리뷰 삭제에 성공했습니다.", HttpStatus.OK);
    }
}
