package com.depromeet.team5.service.impl;

import com.depromeet.team5.domain.review.Review;
import com.depromeet.team5.domain.review.ReviewCreateValue;
import com.depromeet.team5.domain.review.ReviewStatus;
import com.depromeet.team5.domain.review.ReviewUpdateValue;
import com.depromeet.team5.domain.store.Store;
import com.depromeet.team5.domain.user.User;
import com.depromeet.team5.exception.ReviewNotFoundException;
import com.depromeet.team5.exception.StoreNotFoundException;
import com.depromeet.team5.domain.review.ReviewRepository;
import com.depromeet.team5.repository.StoreRepository;
import com.depromeet.team5.domain.review.ReviewService;
import com.depromeet.team5.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final UserService userService;
    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;

    @Override
    @Transactional
    public void saveReview(ReviewCreateValue reviewCreateValue, Long userId, Long storeId) {
        Assert.notNull(reviewCreateValue, "'reviewCreateValue' must not be null");
        Assert.notNull(userId, "'userId' must not be null");
        Assert.notNull(storeId, "'storeId' must not be null");

        User user = userService.getActiveUser(userId);
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new StoreNotFoundException(storeId));
        Review review = Review.from(reviewCreateValue, user, store);
        reviewRepository.save(review);
        this.calculateRating(store);
    }

    @Override
    public Page<Review> getAllByUser(Long userId, Pageable pageable) {
        Assert.notNull(userId, "'userId' must not be null");
        Assert.notNull(pageable, "'pageable' must not be null");

        User user = userService.getActiveUser(userId);
        return reviewRepository.findByUserAndStatus(user, ReviewStatus.POSTED, pageable);
    }

    @Override
    @Transactional
    public Review update(Long userId, Long reviewId, ReviewUpdateValue reviewUpdateValue) {
        Assert.notNull(userId, "'userId' must not be null");
        Assert.notNull(reviewUpdateValue, "'reviewUpdateValue' must not be null");

        User user = userService.getActiveUser(userId);
        Review review = reviewRepository.findByIdAndStatus(reviewId, ReviewStatus.POSTED)
                .map(it -> it.update(user, reviewUpdateValue))
                .orElseThrow(() -> new ReviewNotFoundException(reviewId));
        this.calculateRating(review.getStoreId());
        return reviewRepository.save(review);
    }

    @Override
    @Transactional
    public void delete(Long userId, Long reviewId) {
        Assert.notNull(userId, "'userId' must not be null");
        Assert.notNull(reviewId, "'reviewId' must not be null");

        User user = userService.getActiveUser(userId);
        reviewRepository.findById(reviewId)
                .ifPresent(review -> {
                    review.delete(user);
                    this.calculateRating(review.getStoreId());
                    reviewRepository.save(review);
                });
    }

    // TODO: migration 끝나면 삭제된 리뷰 제외하고 계산하도록 쿼리 수정해야함.
    // TODO: event publish - subscribe 방식으로 변경.
    private void calculateRating(Store store) {
        Float rating = reviewRepository.roundAvgRatingByStoreId(store.getId());
        store.setRating(rating);
        storeRepository.save(store);
    }

    private void calculateRating(Long storeId) {
        Store store = storeRepository.findById(storeId).orElse(null);
        if (store == null) {
            log.error("Failed to calculate rating. storeId: {}", storeId);
            return;
        }
        this.calculateRating(store);
    }
}
