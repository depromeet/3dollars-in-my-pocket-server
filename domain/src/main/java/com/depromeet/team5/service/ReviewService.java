package com.depromeet.team5.service;

import com.depromeet.team5.domain.store.Review;
import com.depromeet.team5.domain.store.ReviewCreateValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {

    void saveReview(ReviewCreateValue reviewCreateValue, Long userId, Long storeId);

    Page<Review> getAllByUser(Long userId, Pageable pageable);
}
