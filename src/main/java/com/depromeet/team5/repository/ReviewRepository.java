package com.depromeet.team5.repository;

import com.depromeet.team5.domain.Review;
import com.depromeet.team5.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends PagingAndSortingRepository<Review, Long> {

    Page<Review> findByUser(User user, Pageable pageable);

    @Query(value = "SELECT ROUND(AVG(rating), 1) FROM review WHERE store_id = :storeId", nativeQuery = true)
    Float findByRatingAvg(@Param("storeId") Long storeId);
}
