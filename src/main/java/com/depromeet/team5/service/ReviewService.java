package com.depromeet.team5.service;

import com.depromeet.team5.domain.Review;
import com.depromeet.team5.dto.ReviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {

    void saveReview(ReviewDto reviewDto, Long userId, Long storeId);

    Page<Review> getAllByUser(Long userId, Pageable pageable);
}
