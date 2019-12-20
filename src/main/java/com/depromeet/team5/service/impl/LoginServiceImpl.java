package com.depromeet.team5.service.impl;

import com.depromeet.team5.domain.User;
import com.depromeet.team5.dto.LoginDto;
import com.depromeet.team5.dto.TokenDto;
import com.depromeet.team5.repository.UserRepository;
import com.depromeet.team5.service.JwtService;
import com.depromeet.team5.service.LoginService;
import com.depromeet.team5.service.SocialService;
import com.depromeet.team5.vo.KakaoUserVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final SocialService socialService;

    @Override
    @Transactional
    public LoginDto login(TokenDto tokenDto) {

        KakaoUserVo userVo = socialService.getKakaoUserInfo(tokenDto);

        User user = userRepository.findBySocialId(userVo.getUserId());

        LoginDto loginDto = new LoginDto();

        if (user == null) {
            User newUser = new User();

            newUser.setSocialId(userVo.getUserId());
            newUser.setNickName(userVo.getUserName());

            userRepository.save(newUser);

            JwtService.TokenRes token = new JwtService.TokenRes(jwtService.create(newUser.getId()));
            loginDto.setToken(token.getToken());

            User user1 = userRepository.findBySocialId(userVo.getUserId());
            loginDto.setUserId(user1.getId());

            return loginDto;

        }

        JwtService.TokenRes token = new JwtService.TokenRes(jwtService.create(user.getId()));
        loginDto.setToken(token.getToken());
        loginDto.setUserId(user.getId());

        return loginDto;
    }
}
