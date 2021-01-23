package com.depromeet.team5.application.user;

import com.depromeet.team5.domain.user.SocialTypes;
import com.depromeet.team5.domain.user.SocialVo;
import com.depromeet.team5.dto.LoginResponse;
import com.depromeet.team5.exception.InvalidAccessTokenException;
import com.depromeet.team5.infrastructure.apple.AppleLoginTokenValidator;
import com.depromeet.team5.infrastructure.kakao.KakaoLoginTokenValidator;
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
    private final KakaoLoginTokenValidator kakaoLoginTokenValidator;
    private final AppleLoginTokenValidator appleLoginTokenValidator;

    @Transactional
    public LoginResponse login(SocialVo socialVo) {

        boolean isValid = true;
        SocialTypes socialType = socialVo.getSocialType();
        String token = socialVo.getToken();

        if (token != null) {
            isValid = checkLoginToken(socialType, token);
        }
        if (!isValid) {
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

    private boolean checkLoginToken(SocialTypes socialType, String token) {

        if (Objects.equals(socialType, SocialTypes.KAKAO)) {
            return kakaoLoginTokenValidator.isValid(token);
        } else if (Objects.equals(socialType, SocialTypes.APPLE)) {
            return appleLoginTokenValidator.isValid(token);
        }

        return false;
    }
}
