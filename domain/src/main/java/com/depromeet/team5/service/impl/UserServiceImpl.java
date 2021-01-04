package com.depromeet.team5.service.impl;

import com.depromeet.team5.domain.user.SocialTypes;
import com.depromeet.team5.domain.user.User;
import com.depromeet.team5.domain.user.UserStatusType;
import com.depromeet.team5.domain.user.WithdrawalUser;
import com.depromeet.team5.exception.NickNameDuplicatedException;
import com.depromeet.team5.exception.UserNotFoundException;
import com.depromeet.team5.exception.WithdrawalUserException;
import com.depromeet.team5.repository.UserRepository;
import com.depromeet.team5.repository.WithdrawalUserRepository;
import com.depromeet.team5.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final WithdrawalUserRepository withdrawalUserRepository;

    @NonNull
    @Transactional(readOnly = true)
    @Override
    public User getActiveUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        if (user.isWithdrawal()) {
            throw new WithdrawalUserException(userId);
        }
        return user;
    }

    @Transactional
    @Override
    public User createUser(String socialId, SocialTypes socialTypes) {
        User user = User.of(socialId, socialTypes);
        userRepository.save(user);
        return user;
    }

    @Transactional
    @Override
    public User getOrCreateUser(String socialId, SocialTypes socialType) {

        Optional<WithdrawalUser> withdrawalUserOptional =
                withdrawalUserRepository.findBySocialIdAndSocialType(socialId, socialType);
        if (withdrawalUserOptional.isPresent()) {
            WithdrawalUser withdrawalUser = withdrawalUserOptional.get();
            User user = userRepository.findById(withdrawalUser.getUserId())
                    .map(it -> it.resignin(userRepository, withdrawalUser))
                    .orElseThrow(() -> new UserNotFoundException(withdrawalUser.getUserId()));
            withdrawalUserRepository.delete(withdrawalUser);
            return user;
        }
        return userRepository.findBySocialIdAndSocialType(socialId, socialType)
                .orElseGet(() -> createUser(socialId, socialType));
    }

    @Transactional
    @Override
    public void signOut(Long userId) {
        userRepository.findById(userId)
                .filter(it -> it.getStatus() == UserStatusType.ACTIVE)
                .ifPresent(user -> {
                    withdrawalUserRepository.findById(user.getId())
                            .map(it -> it.update(user))
                            .orElseGet(() -> withdrawalUserRepository.save(WithdrawalUser.from(user)));
                    user.signOut();
                });
    }

    @Transactional
    @Override
    public void kakaoDeregister(String header, String userId, String referrerType) {
        Optional<User> user = userRepository.findBySocialIdAndSocialType(userId, SocialTypes.KAKAO);
        user.ifPresent(it -> signOut(it.getId()));
    }

    @Transactional
    @Override
    public void setNickname(Long userId, String nickname) {
        Assert.notNull(userId, "'userId' must not be null");
        Assert.hasText(nickname, "'nickname' must not be null, empty and blank");

        String trimmedNickname = StringUtils.trimWhitespace(nickname);
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        if (Objects.equals(trimmedNickname, user.getName())) {
            return;
        }
        if (userRepository.findFirst1ByNameAndStatus(trimmedNickname, UserStatusType.ACTIVE).isPresent()) {
            throw new NickNameDuplicatedException(userId, nickname);
        }
        user.setName(nickname);
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