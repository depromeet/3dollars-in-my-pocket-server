package com.depromeet.team5.application.user;

import com.depromeet.team5.domain.user.User;
import com.depromeet.team5.dto.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserAssembler {
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
}

