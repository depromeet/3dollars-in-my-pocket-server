package com.depromeet.team5.service.impl;

import com.depromeet.team5.domain.User;
import com.depromeet.team5.dto.LoginDto;
import com.depromeet.team5.dto.UserDto;
import com.depromeet.team5.repository.UserRepository;
import com.depromeet.team5.service.JwtService;
import com.depromeet.team5.service.LoginService;
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

    @Override
    @Transactional
    public LoginDto login(UserDto userDto) {

        User user = userRepository.findBySocialIdAndSocialType(userDto.getSocialId(), userDto.getSocialType())
                .orElseGet(() -> createUser(userDto));

        JwtService.TokenRes token = new JwtService.TokenRes(jwtService.create(user.getId()));

        LoginDto loginDto = new LoginDto();
        loginDto.setToken(token.getToken());
        loginDto.setUserId(user.getId());

        return loginDto;
    }

    private User createUser(UserDto userDto) {
        User user = User.from(userDto);
        userRepository.save(user);
        return user;
    }
}
