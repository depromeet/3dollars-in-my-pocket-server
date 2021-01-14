package com.depromeet.team5.service.impl;

import com.depromeet.team5.domain.user.SocialTypes;
import com.depromeet.team5.domain.user.SocialVo;
import com.depromeet.team5.domain.user.User;
import com.depromeet.team5.domain.user.UserStatusType;
import com.depromeet.team5.domain.user.WithdrawalUser;
import com.depromeet.team5.exception.NickNameDuplicatedException;
import com.depromeet.team5.exception.UserNotFoundException;
import com.depromeet.team5.exception.WithdrawalUserException;
import com.depromeet.team5.repository.UserRepository;
import com.depromeet.team5.repository.WithdrawalUserRepository;
import com.depromeet.team5.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final UserRepository userRepository;
    private final WithdrawalUserRepository withdrawalUserRepository;

    @Override
    @Transactional
    public User login(SocialVo socialVo) {
        return getOrCreateUser(socialVo.getSocialId(), socialVo.getSocialType());
    }

    @Override
    @Transactional(readOnly = true)
    public User userInfo(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        if (user.getStatus() == UserStatusType.INACTIVE)
            throw new WithdrawalUserException(userId);
        return user;
    }

    @Override
    @Transactional
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
        user.setName(trimmedNickname);
    }

    private User getOrCreateUser(String socialId, SocialTypes socialType) {

        Optional<WithdrawalUser> withdrawalUserOptional =
                withdrawalUserRepository.findBySocialIdAndSocialType(socialId, socialType);
        if (withdrawalUserOptional.isPresent()) {
            WithdrawalUser withdrawalUser = withdrawalUserOptional.get();
            User user = userRepository.findById(withdrawalUser.getUserId())
                    .map(it -> it.resignin(userRepository, withdrawalUser))
                    .orElseGet(() -> createUser(socialId, socialType));
            withdrawalUserRepository.delete(withdrawalUser);
            return user;
        }
        return userRepository.findBySocialIdAndSocialType(socialId, socialType)
                .orElseGet(() -> createUser(socialId, socialType));
    }

    private User createUser(String socialId, SocialTypes socialTypes) {
        User user = User.of(socialId, socialTypes);
        userRepository.save(user);
        return user;
    }
}
