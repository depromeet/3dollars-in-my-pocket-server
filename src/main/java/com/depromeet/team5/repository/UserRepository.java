package com.depromeet.team5.repository;

import com.depromeet.team5.domain.SocialTypes;
import com.depromeet.team5.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findBySocialIdAndSocialType(String socialId, SocialTypes socialType);
}
