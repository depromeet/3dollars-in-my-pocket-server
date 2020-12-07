package com.depromeet.team5.repository;

import com.depromeet.team5.domain.user.SocialTypes;
import com.depromeet.team5.domain.user.User;
import com.depromeet.team5.domain.user.UserStatusType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findBySocialIdAndSocialType(String socialId, SocialTypes socialType);
    Optional<User> findByNameLike(String nickName);
    Optional<User> findByIdAndStatus(Long userId, UserStatusType type);
    long countByCreatedAtBetween(LocalDateTime createdAtBegin, LocalDateTime createdAtEnd);
    long countByCreatedAtLessThan(LocalDateTime createdAt);
    long countByUpdatedAtBetweenAndStatus(LocalDateTime updatedAtBegin, LocalDateTime updatedAtEnd, UserStatusType userStatusType);
}
