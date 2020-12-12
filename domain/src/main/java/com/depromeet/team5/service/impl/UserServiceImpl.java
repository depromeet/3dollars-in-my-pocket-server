package com.depromeet.team5.service.impl;

import com.depromeet.team5.domain.user.SocialTypes;
import com.depromeet.team5.domain.user.User;
import com.depromeet.team5.domain.user.UserStatusType;
import com.depromeet.team5.domain.user.WithdrawalUser;
import com.depromeet.team5.repository.UserRepository;
import com.depromeet.team5.repository.WithdrawalUserRepository;
import com.depromeet.team5.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final WithdrawalUserRepository withdrawalUserRepository;

    @Transactional
    @Override
    public void signout(Long userId) {
        userRepository.findById(userId)
                .filter(it -> it.getStatus() == UserStatusType.ACTIVE)
                .ifPresent(user -> {
                    withdrawalUserRepository.findById(user.getId())
                            .map(it -> it.update(user))
                            .orElseGet(() -> withdrawalUserRepository.save(WithdrawalUser.from(user)));
                    user.signout();
                });
    }

    @Transactional
    @Override
    public void kakaoDeregister(String header, String userId, String referrerType) {
        Optional<User> user = userRepository.findBySocialIdAndSocialType(userId, SocialTypes.KAKAO);
        user.ifPresent(it -> signout(it.getId()));
    }

    @Transactional(readOnly = true)
    @Override
    public long countUserByCreatedDateEqualTo(LocalDate localDate) {
        LocalDateTime startOfDay = localDate.atStartOfDay();
        return userRepository.countByCreatedAtBetween(startOfDay, startOfDay.plusDays(1L).minusNanos(1L));
    }

    @Transactional(readOnly = true)
    @Override
    public long countByCreatedAtLessThan(LocalDateTime localDateTime) {
        return userRepository.countByCreatedAtLessThan(localDateTime);
    }

    @Transactional(readOnly = true)
    @Override
    public long countByUpdatedDateEqualToAndStatus(LocalDate localDate, UserStatusType userStatusType) {
        LocalDateTime startOfDay = localDate.atStartOfDay();
        return userRepository.countByUpdatedAtBetweenAndStatus(
                startOfDay, startOfDay.plusDays(1L).minusNanos(1L), userStatusType);
    }
}