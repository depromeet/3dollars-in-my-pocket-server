package com.depromeet.team5.repository;

import com.depromeet.team5.domain.user.SocialTypes;
import com.depromeet.team5.domain.user.WithdrawalUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WithdrawalUserRepository extends JpaRepository<WithdrawalUser, Long> {
    Optional<WithdrawalUser> findBySocialIdAndSocialType(String socialId, SocialTypes socialType);
}
