package com.depromeet.team5.repository;

import com.depromeet.team5.domain.DeleteRequestId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeleteRepository extends JpaRepository<DeleteRequestId, Long> {
    Optional<DeleteRequestId> findByUserIdLike(Long userId);
}
