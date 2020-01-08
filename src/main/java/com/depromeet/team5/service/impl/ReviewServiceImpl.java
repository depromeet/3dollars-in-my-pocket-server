package com.depromeet.team5.service.impl;

import com.depromeet.team5.domain.Review;
import com.depromeet.team5.domain.User;
import com.depromeet.team5.dto.ReviewDto;
import com.depromeet.team5.exception.UserNotFoundException;
import com.depromeet.team5.repository.ReviewRepository;
import com.depromeet.team5.repository.UserRepository;
import com.depromeet.team5.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public void saveReview(ReviewDto reviewDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Review review = Review.from(reviewDto, user);
        reviewRepository.save(review);
    }

    @Override
    public List<ReviewDto> getAllByUser(Long userId) {
        return null;
    }

    @Override
    public List<ReviewDto> getAllByStore(Long storeId) {
        return null;
    }

    @Override
    public Review getDetail(Long reviewId) {
        return null;
    }

    @Override
    public void updateReview(ReviewDto reviewDto, Long reviewId) {

    }

    @Override
    public void deleteReview(Long reviewId) {

    }
}
