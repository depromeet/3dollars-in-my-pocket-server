package com.depromeet.team5.service;

import com.depromeet.team5.domain.user.SocialVo;
import com.depromeet.team5.domain.user.User;

public interface LoginService {
    User login(SocialVo socialVo);

    User userInfo(Long userId);

    void setNickname(Long userId, String nickName);
}
