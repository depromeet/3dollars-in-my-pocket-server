package com.depromeet.team5.service;

import com.depromeet.team5.domain.Review;
import com.depromeet.team5.dto.ReviewDto;

import java.util.List;

public interface ReviewService {

    void saveReview(ReviewDto reviewDto, Long userId);

    List<ReviewDto> getAllByUser(Long userId);

    List<ReviewDto> getAllByStore(Long storeId);

    Review getDetail(Long reviewId);

    void updateReview(ReviewDto reviewDto, Long reviewId);

    void deleteReview(Long reviewId);
}
