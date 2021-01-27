package com.depromeet.team5.application.user;

import com.depromeet.team5.domain.user.User;
import com.depromeet.team5.dto.LoginResponse;
import com.depromeet.team5.dto.UserResponse;
import com.depromeet.team5.infrastructure.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAssembler {

    private final JwtService jwtService;

    public UserResponse toUserResponse(User user) {
        if (user == null) {
            return null;
        }
        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setState(user.getState());
        userResponse.setStatus(user.getStatus().name());
        userResponse.setCreatedAt(user.getCreatedAt());
        userResponse.setUpdatedAt(user.getUpdatedAt());
        return userResponse;
    }

    public LoginResponse toLoginResponse(User user) {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUserId(user.getId());
        loginResponse.setState(user.getState());
        loginResponse.setToken(jwtService.create(user.getId()));
        return loginResponse;
    }
}

