package com.depromeet.team5.application.review;

import com.depromeet.team5.domain.review.ReviewUpdateValue;
import com.depromeet.team5.dto.ReviewResponse;
import com.depromeet.team5.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ReviewApplicationService {
    private final ReviewService reviewService;
    private final ReviewAssembler reviewAssembler;

    @Transactional
    public ReviewResponse updateReview(Long userId, Long reviewId, ReviewUpdateValue reviewUpdateValue) {
        return reviewAssembler.toReviewResponse(
                reviewService.update(userId, reviewId, reviewUpdateValue)
        );
    }

    @Transactional
    public void deleteReview(Long userId, Long reviewId) {
        reviewService.delete(userId, reviewId);
    }
}
