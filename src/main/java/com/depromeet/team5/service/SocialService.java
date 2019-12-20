package com.depromeet.team5.service;

import com.depromeet.team5.dto.TokenDto;
import com.depromeet.team5.vo.GoogleUserVo;
import com.depromeet.team5.vo.KakaoUserVo;

public interface SocialService {
    KakaoUserVo getKakaoUserInfo(TokenDto dto);
    GoogleUserVo getGoogleUserInfo(TokenDto dto);
}