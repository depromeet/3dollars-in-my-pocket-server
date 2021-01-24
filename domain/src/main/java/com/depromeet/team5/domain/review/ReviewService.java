package com.depromeet.team5.domain.review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ReviewService {

    void saveReview(ReviewCreateValue reviewCreateValue, Long userId, Long storeId);

    Page<Review> getAllByUser(Long userId, Pageable pageable);

    Review update(Long userId, Long reviewId, ReviewUpdateValue reviewUpdateValue);

    void delete(Long userId, Long reviewId);

    Optional<Review> getReview(Long reviewId);
}
