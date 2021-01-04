package com.depromeet.team5.application.user;

import com.depromeet.team5.domain.user.SocialTypes;
import com.depromeet.team5.domain.user.SocialVo;
import com.depromeet.team5.dto.LoginResponse;
import com.depromeet.team5.exception.InvalidAccessTokenException;
import com.depromeet.team5.infrastructure.kakao.KakaoTokenVerifier;
import com.depromeet.team5.domain.user.User;
import com.depromeet.team5.dto.UserResponse;
import com.depromeet.team5.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserApplicationService {

    private final UserService userService;
    private final UserAssembler userAssembler;
    private final KakaoTokenVerifier kakaoTokenVerifier;

    public LoginResponse login(SocialVo socialVo) {
        boolean isVerified = false;
        SocialTypes socialType = socialVo.getSocialType();
        String token = socialVo.getToken();
        if (socialType.equals(SocialTypes.KAKAO)) {
            isVerified = kakaoTokenVerifier.isVerified(token);
        }
        if (isVerified) {
            User user = userService.getOrCreateUser(socialVo.getSocialId(), socialType);
            return userAssembler.toLoginResponse(user);
        }
        throw new InvalidAccessTokenException();
    }

    public UserResponse getMe(Long userId) {
        User user = userService.getActiveUser(userId);
        return userAssembler.toUserResponse(user);
    }
}
