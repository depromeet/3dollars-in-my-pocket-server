package com.depromeet.team5.application.user;

import com.depromeet.team5.domain.user.SocialType;
import com.depromeet.team5.domain.user.SocialVo;
import com.depromeet.team5.dto.LoginResponse;
import com.depromeet.team5.exception.InvalidAccessTokenException;
import com.depromeet.team5.infrastructure.apple.AppleLoginTokenVerifier;
import com.depromeet.team5.infrastructure.kakao.KakaoLoginTokenVerifier;
import com.depromeet.team5.domain.user.User;
import com.depromeet.team5.dto.UserResponse;
import com.depromeet.team5.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserApplicationService {

    private final UserService userService;
    private final UserAssembler userAssembler;
    private final KakaoLoginTokenVerifier kakaoLoginTokenVerifier;
    private final AppleLoginTokenVerifier appleLoginTokenVerifier;

    @Transactional
    public LoginResponse login(SocialVo socialVo) {

        SocialType socialType = socialVo.getSocialType();
        String token = socialVo.getToken();

        if (token != null && !isValid(socialType, token)) {
            throw new InvalidAccessTokenException();
        }

        User user = userService.getOrCreateUser(socialVo.getSocialId(), socialType);
        return userAssembler.toLoginResponse(user);
    }

    @Transactional(readOnly = true)
    public UserResponse getMe(Long userId) {

        User user = userService.getActiveUser(userId);
        return userAssembler.toUserResponse(user);
    }

    private boolean isValid(SocialType socialType, String token) {

        if (Objects.equals(socialType, SocialType.KAKAO)) {
            return kakaoLoginTokenVerifier.isVerified(token);
        } else if (Objects.equals(socialType, SocialType.APPLE)) {
            return appleLoginTokenVerifier.isVerified(token);
        }

        return false;
    }
}
