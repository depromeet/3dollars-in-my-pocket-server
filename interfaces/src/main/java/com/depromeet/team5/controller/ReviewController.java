package com.depromeet.team5.controller;

import com.depromeet.team5.application.review.ReviewApplicationService;
import com.depromeet.team5.application.security.Auth;
import com.depromeet.team5.domain.review.Review;
import com.depromeet.team5.domain.review.ReviewCreateValue;
import com.depromeet.team5.domain.review.ReviewService;
import com.depromeet.team5.domain.review.ReviewUpdateValue;
import com.depromeet.team5.dto.*;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@Api(value = "Review")
@CrossOrigin(origins = {"*"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/review")
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewApplicationService reviewApplicationService;

    @ApiOperation("리뷰를 등록합니다. 인증이 필요한 요청입니다.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @Auth
    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody ReviewDto reviewDto, @RequestParam Long userId, @RequestParam Long storeId) {
        reviewService.saveReview(ReviewCreateValue.of(reviewDto.getContents(), reviewDto.getRating()), userId, storeId);
        return new ResponseEntity<>("리뷰 등록에 성공했습니다.", HttpStatus.OK);
    }

    @ApiOperation("사용자가 작성한 리뷰를 조회합니다. 인증이 필요한 요청입니다.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @Auth
    @GetMapping("/user")
    public ResponseEntity<ReviewPomDto> getAllByUser(@RequestParam Long userId, @RequestParam Integer page) {
        Pageable pageable = PageRequest.of(page - 1, 3, Sort.by("createdAt").descending());
        Page<Review> reviewPage = reviewService.getAllByUser(userId, pageable);
        return ResponseEntity.ok(
                ReviewPomDto.from(
                        reviewPage.getContent(),
                        reviewPage.getTotalElements(),
                        reviewPage.getTotalPages()
                )
        );
    }

    @ApiOperation("특정 리뷰를 조회합니다. 인증이 필요한 요청입니다.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @Auth
    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewDetailResponse> getReview(@PathVariable Long reviewId) {
        return ResponseEntity.ok(reviewApplicationService.getDetailReview(reviewId));
    }

    @ApiOperation("사용자가 작성한 리뷰를 수정합니다. 인증이 필요한 요청입니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Access token is not valid"),
            @ApiResponse(code = 403, message = "User is not author of review"),
            @ApiResponse(code = 404, message = "Review does not exist"),
            @ApiResponse(code = 500, message = "Server error"),
    })
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @Auth
    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewResponse> update(
            @ModelAttribute("userId") @ApiIgnore Long userId,
            @PathVariable Long reviewId,
            @RequestBody @Valid ReviewUpdateRequest reviewUpdateRequest
    ) {
        return ResponseEntity.ok(
                reviewApplicationService.updateReview(
                        userId,
                        reviewId,
                        ReviewUpdateValue.of(
                                reviewUpdateRequest.getContent(),
                                reviewUpdateRequest.getRating()
                        )
                )
        );
    }

    @ApiOperation("사용자가 작성한 리뷰를 삭제합니다. 인증이 필요한 요청입니다.")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Success"),
            @ApiResponse(code = 401, message = "Access token is not valid"),
            @ApiResponse(code = 403, message = "User is not author of review"),
            @ApiResponse(code = 500, message = "Server error"),
    })
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @Auth
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Object> delete(
            @ModelAttribute("userId") @ApiIgnore Long userId,
            @PathVariable Long reviewId
    ) {
        reviewApplicationService.deleteReview(userId, reviewId);
        return ResponseEntity.noContent().build();
    }
}
