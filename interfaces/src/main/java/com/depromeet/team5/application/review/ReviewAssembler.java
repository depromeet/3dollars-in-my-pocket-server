package com.depromeet.team5.application.review;

import com.depromeet.team5.domain.review.Review;
import com.depromeet.team5.dto.ReviewResponse;
import org.springframework.stereotype.Component;

@Component
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
}
