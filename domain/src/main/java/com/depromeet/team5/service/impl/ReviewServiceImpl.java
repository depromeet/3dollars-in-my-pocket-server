package com.depromeet.team5.service.impl;

import com.depromeet.team5.domain.Review;
import com.depromeet.team5.domain.Store;
import com.depromeet.team5.domain.user.User;
import com.depromeet.team5.dto.ReviewDto;
import com.depromeet.team5.dto.ReviewPomDto;
import com.depromeet.team5.exception.StoreNotFoundException;
import com.depromeet.team5.exception.UserNotFoundException;
import com.depromeet.team5.repository.ReviewRepository;
import com.depromeet.team5.repository.StoreRepository;
import com.depromeet.team5.repository.UserRepository;
import com.depromeet.team5.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;

    @Override
    public void saveReview(ReviewDto reviewDto, Long userId, Long storeId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new StoreNotFoundException(storeId));
        Review review = Review.from(reviewDto, user, store);
        reviewRepository.save(review);

        Float rating = reviewRepository.findByRatingAvg(storeId);
        store.setRating(rating);
        storeRepository.save(store);
    }

    @Override
    public ReviewPomDto getAllByUser(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Page<Review> reviewList = reviewRepository.findByUser(user, pageable);
        return ReviewPomDto.from(reviewList.getContent(), reviewList.getTotalElements(), reviewList.getTotalPages());
    }
}
