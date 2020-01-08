package com.depromeet.team5.controller;

import com.depromeet.team5.dto.ReviewDto;
import com.depromeet.team5.dto.ReviewUpdateDto;
import com.depromeet.team5.service.ReviewService;
import io.swagger.annotations.Api;
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
    @PostMapping("/save")
    public ResponseEntity<String> save(ReviewDto reviewDto, @RequestParam Long userId) {
        reviewService.saveReview(reviewDto, userId);
        return new ResponseEntity<>("리뷰 등록에 성공했습니다.", HttpStatus.OK);
    }

    @ApiOperation("사용자가 작성한 리뷰를 조회합니다. 인증이 필요한 요청입니다.")
    @GetMapping("/get")
    public ResponseEntity<List<ReviewDto>> getAllByUser(@RequestParam Long userId) {
        return new ResponseEntity<>(reviewService.getAllByUser(userId), HttpStatus.OK);
    }

    @ApiOperation("리뷰를 수정합니다. 인증이 필요한 요청입니다.")
    @PutMapping("/update")
    public ResponseEntity<String> update(ReviewUpdateDto reviewUpdateDto, @RequestParam Long reviewId) {
        reviewService.updateReview(reviewUpdateDto, reviewId);
        return new ResponseEntity<>("리뷰 수정에 성공했습니다.", HttpStatus.OK);
    }

    @ApiOperation("리뷰를 삭제합니다. 인증이 필요한 요청입니다.")
    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam Long reviewId) {
        reviewService.deleteReview(reviewId);
        return new ResponseEntity<>("리뷰 삭제에 성공했습니다.", HttpStatus.OK);
    }
}
