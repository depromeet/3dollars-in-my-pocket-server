package com.depromeet.team5.repository;

import com.depromeet.team5.domain.review.Review;
import com.depromeet.team5.domain.review.ReviewStatus;
import com.depromeet.team5.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReviewRepository extends PagingAndSortingRepository<Review, Long> {

    Page<Review> findByUserAndStatus(User user, ReviewStatus status, Pageable pageable);

    Optional<Review> findByIdAndStatus(Long reviewId, ReviewStatus status);

    @Query(value = "SELECT ROUND(AVG(rating), 1) FROM review WHERE store_id = :storeId AND status = :status", nativeQuery = true)
    Float roundAvgRatingByStoreIdAndStatus(@Param("storeId") Long storeId, @Param("status") ReviewStatus status);
}
