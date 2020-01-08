package com.depromeet.team5.repository;

import com.depromeet.team5.domain.Review;
import com.depromeet.team5.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByUser(User user);
}
