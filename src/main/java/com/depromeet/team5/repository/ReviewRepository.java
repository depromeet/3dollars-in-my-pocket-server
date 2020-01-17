package com.depromeet.team5.repository;

import com.depromeet.team5.domain.Review;
import com.depromeet.team5.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByUser(User user);

    @Query(value = "SELECT ROUND(AVG(rating), 1) FROM review WHERE store_id = :storeId",
    nativeQuery = true)
    Float findByRatingAvg(@Param("storeId") Long storeId);
}
