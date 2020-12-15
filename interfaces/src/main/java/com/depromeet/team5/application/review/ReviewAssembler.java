package com.depromeet.team5.application.review;

import com.depromeet.team5.domain.review.Review;
import com.depromeet.team5.dto.ReviewDetailResponse;
import com.depromeet.team5.dto.ReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewAssembler {
    public ReviewResponse toReviewResponse(Review review) {
        if (review == null) {
            return null;
        }
        ReviewResponse reviewResponse = new ReviewResponse();
        reviewResponse.setId(review.getId());
        reviewResponse.setUserId(review.getUser().getId());
        reviewResponse.setStoreId(review.getStoreId());
        reviewResponse.setContent(review.getContents());
        reviewResponse.setRating(review.getRating());
        return reviewResponse;
    }

    public ReviewDetailResponse toReviewDetailResponse(Review review) {
        if (review == null) {
            return null;
        }
        ReviewDetailResponse reviewDetailResponse = new ReviewDetailResponse();
        reviewDetailResponse.setId(review.getId());
        reviewDetailResponse.setStoreName(review.getStoreName());
        reviewDetailResponse.setCategory(review.getCategory());
        reviewDetailResponse.setContents(review.getContents());
        reviewDetailResponse.setRating(review.getRating());
        reviewDetailResponse.setUser(review.getUser());
        reviewDetailResponse.setCreatedAt(review.getCreatedAt());
        reviewDetailResponse.setUpdatedAt(review.getUpdatedAt());
        return reviewDetailResponse;
    }
}
