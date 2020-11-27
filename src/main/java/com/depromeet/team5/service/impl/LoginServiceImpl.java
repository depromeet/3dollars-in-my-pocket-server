package com.depromeet.team5.service.impl;

import com.depromeet.team5.domain.SocialTypes;
import com.depromeet.team5.domain.user.User;
import com.depromeet.team5.domain.user.UserStatusType;
import com.depromeet.team5.domain.user.WithdrawalUser;
import com.depromeet.team5.dto.LoginDto;
import com.depromeet.team5.dto.UserDto;
import com.depromeet.team5.exception.NickNameCheckException;
import com.depromeet.team5.exception.UserNotFoundException;
import com.depromeet.team5.exception.WithdrawalUserException;
import com.depromeet.team5.repository.UserRepository;
import com.depromeet.team5.repository.WithdrawalUserRepository;
import com.depromeet.team5.service.JwtService;
import com.depromeet.team5.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final UserRepository userRepository;
    private final WithdrawalUserRepository withdrawalUserRepository;
    private final JwtService jwtService;

    @Override
    @Transactional
    public LoginDto login(UserDto userDto) {
        User user = getOrCreateUser(userDto);

        JwtService.TokenRes token = new JwtService.TokenRes(jwtService.create(user.getId()));

        LoginDto loginDto = new LoginDto();
        loginDto.setToken(token.getToken());
        loginDto.setUserId(user.getId());
        loginDto.setState(user.getState());
        return loginDto;
    }

    @Override
    @Transactional
    public User userInfo(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        if (user.getStatus() == UserStatusType.INACTIVE)
            throw new WithdrawalUserException(userId);
        return user;
    }

    @Override
    @Transactional
    public void setNickname(Long userId, String nickName) {
        if (userRepository.findByNameLike(nickName).isPresent()) {
            throw new NickNameCheckException();
        }
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        user.setName(nickName);
        userRepository.save(user);
    }

    private User getOrCreateUser(UserDto userDto) {
        String socialId = userDto.getSocialId();
        SocialTypes socialType = userDto.getSocialType();

        Optional<User> userOptional = userRepository.findBySocialIdAndSocialType(socialId, socialType);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        Optional<WithdrawalUser> withdrawalUserOptional =
                withdrawalUserRepository.findBySocialIdAndSocialType(socialId, socialType);
        if (withdrawalUserOptional.isPresent()) {
            WithdrawalUser withdrawalUser = withdrawalUserOptional.get();
            User user = userRepository.save(User.from(withdrawalUser));
            withdrawalUserRepository.delete(withdrawalUser);
            return user;
        }
        return createUser(userDto);
    }

    private User createUser(UserDto userDto) {
        User user = User.from(userDto);
        userRepository.save(user);
        return user;
    }
}
