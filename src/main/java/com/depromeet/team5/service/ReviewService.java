package com.depromeet.team5.service;

import com.depromeet.team5.dto.ReviewDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewService {

    void saveReview(ReviewDto reviewDto, Long userId, Long storeId);

    List<ReviewDto> getAllByUser(Long userId, Pageable pageable);
}
