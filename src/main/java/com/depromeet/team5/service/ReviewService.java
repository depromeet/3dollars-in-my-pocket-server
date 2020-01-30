package com.depromeet.team5.service;

import com.depromeet.team5.dto.ReviewDto;
import com.depromeet.team5.dto.ReviewPomDto;
import org.springframework.data.domain.Pageable;

public interface ReviewService {

    void saveReview(ReviewDto reviewDto, Long userId, Long storeId);

    ReviewPomDto getAllByUser(Long userId, Pageable pageable);
}
