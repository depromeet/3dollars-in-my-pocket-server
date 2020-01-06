package com.depromeet.team5.service;

import com.depromeet.team5.domain.User;
import com.depromeet.team5.dto.LoginDto;
import com.depromeet.team5.dto.UserDto;

public interface LoginService {
    LoginDto login(UserDto userDto);
    User userInfo(Long userId);
    void setNickname(Long userId, String nickName);
}
