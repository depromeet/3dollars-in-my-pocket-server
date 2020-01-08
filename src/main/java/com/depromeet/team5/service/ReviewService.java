package com.depromeet.team5.service;

import com.depromeet.team5.domain.Review;
import com.depromeet.team5.dto.ReviewDto;
import com.depromeet.team5.dto.ReviewUpdateDto;

import java.util.List;

public interface ReviewService {

    void saveReview(ReviewDto reviewDto, Long userId);

    List<ReviewDto> getAllByUser(Long userId);

    void updateReview(ReviewUpdateDto reviewUpdateDto, Long reviewId);

    void deleteReview(Long reviewId);
}
