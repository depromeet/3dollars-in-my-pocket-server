package com.depromeet.team5.repository;

import com.depromeet.team5.domain.SocialTypes;
import com.depromeet.team5.domain.user.User;
import com.depromeet.team5.domain.user.UserStatusType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findBySocialIdAndSocialType(String socialId, SocialTypes socialType);

    List<User> findByNameAndStatus(String name, UserStatusType type);

    Optional<User> findByIdAndStatus(Long userId, UserStatusType type);
}
