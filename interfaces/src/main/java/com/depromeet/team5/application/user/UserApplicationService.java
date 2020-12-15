package com.depromeet.team5.application.user;

import com.depromeet.team5.service.LoginService;
import com.depromeet.team5.domain.user.User;
import com.depromeet.team5.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserApplicationService {
    private final LoginService loginService;
    private final UserAssembler userAssembler;

    public UserResponse getMe(Long userId) {
        User user = loginService.userInfo(userId);
        return userAssembler.toUserResponse(user);
    }
}
