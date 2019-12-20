package com.depromeet.team5.service;

import com.depromeet.team5.dto.LoginDto;
import com.depromeet.team5.dto.TokenDto;

public interface LoginService {
    LoginDto login(TokenDto tokenDto);
}
