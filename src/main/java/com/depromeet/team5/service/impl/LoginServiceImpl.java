package com.depromeet.team5.service.impl;

import com.depromeet.team5.domain.SocialTypes;
import com.depromeet.team5.domain.user.User;
import com.depromeet.team5.domain.user.UserStatusType;
import com.depromeet.team5.domain.user.WithdrawalUser;
import com.depromeet.team5.dto.LoginDto;
import com.depromeet.team5.dto.UserDto;
import com.depromeet.team5.exception.NickNameDuplicatedException;
import com.depromeet.team5.exception.UserNotFoundException;
import com.depromeet.team5.exception.WithdrawalUserException;
import com.depromeet.team5.repository.UserRepository;
import com.depromeet.team5.repository.WithdrawalUserRepository;
import com.depromeet.team5.service.JwtService;
import com.depromeet.team5.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final UserRepository userRepository;
    private final WithdrawalUserRepository withdrawalUserRepository;
    private final JwtService jwtService;

    @Override
    @Transactional
    public LoginDto login(UserDto userDto) {
        User user = getOrCreateUser(userDto);

        JwtService.TokenRes token = new JwtService.TokenRes(jwtService.create(user.getId()));

        LoginDto loginDto = new LoginDto();
        loginDto.setToken(token.getToken());
        loginDto.setUserId(user.getId());
        loginDto.setState(user.getState());
        return loginDto;
    }

    @Override
    @Transactional
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
        if (!trimmedNickname.equals(user.getName()) && userRepository.findByNameLike(trimmedNickname).isPresent()) {
            throw new NickNameDuplicatedException(userId, nickname);
        }
        user.setName(nickname);
    }

    private User getOrCreateUser(UserDto userDto) {
        String socialId = userDto.getSocialId();
        SocialTypes socialType = userDto.getSocialType();

        Optional<WithdrawalUser> withdrawalUserOptional =
                withdrawalUserRepository.findBySocialIdAndSocialType(socialId, socialType);
        if (withdrawalUserOptional.isPresent()) {
            WithdrawalUser withdrawalUser = withdrawalUserOptional.get();
            User user = userRepository.findById(withdrawalUser.getUserId())
                    .map(it -> it.resignin(withdrawalUser))
                    .orElseThrow(() -> new UserNotFoundException(withdrawalUser.getUserId()));
            withdrawalUserRepository.delete(withdrawalUser);
            return user;
        }
        return userRepository.findBySocialIdAndSocialType(socialId, socialType)
                .orElseGet(() -> createUser(userDto));
    }

    private User createUser(UserDto userDto) {
        User user = User.from(userDto);
        userRepository.save(user);
        return user;
    }
}
