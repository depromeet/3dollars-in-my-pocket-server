package com.depromeet.team5.repository;

import com.depromeet.team5.domain.DeleteRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeleteRepository extends JpaRepository<DeleteRequest, Long> {
    Optional<DeleteRequest> findByUserIdLike(Long userId);
}
