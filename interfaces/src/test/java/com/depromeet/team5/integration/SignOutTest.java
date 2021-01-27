package com.depromeet.team5.integration;

import com.depromeet.team5.Team5InterfacesApplication;
import com.depromeet.team5.domain.user.SocialType;
import com.depromeet.team5.domain.user.UserStatusType;
import com.depromeet.team5.dto.LoginResponse;
import com.depromeet.team5.dto.UserDto;
import com.depromeet.team5.integration.api.UserTestController;
import com.depromeet.team5.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest(classes = Team5InterfacesApplication.class)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class SignOutTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;

    private UserTestController userTestController;

    @BeforeEach
    void setUp() {
        userTestController = new UserTestController(mockMvc, objectMapper);
    }

    @Test
    void siginout() throws Exception {
        // given
        LoginResponse loginResponse = this.login( "kakao1", SocialType.KAKAO);
        // when
        String token = loginResponse.getToken();
        userTestController.signout(token);
        // then
        assertThat(userRepository.findBySocialIdAndSocialType("kakao1", SocialType.KAKAO)
                .filter(it -> it.getStatus() == UserStatusType.INACTIVE)).isPresent();
    }

    @Test
    void signout_signin() throws Exception {
        // given
        LoginResponse firstSignUpResult = this.login( "kakao1", SocialType.KAKAO);
        String token = firstSignUpResult.getToken();
        userTestController.signout(token);
        assertThat(userRepository.findBySocialIdAndSocialType("kakao1", SocialType.KAKAO)
                .filter(it -> it.getStatus() == UserStatusType.INACTIVE)).isPresent();
        // when
        LoginResponse loginResponse = this.login("kakao1", SocialType.KAKAO);
        // then
        assertThat(loginResponse.getUserId()).isEqualTo(firstSignUpResult.getUserId());
        assertThat(loginResponse.getState()).isFalse();
    }

    private LoginResponse login(String socialId, SocialType socialType) throws Exception {
        UserDto userDto = new UserDto();
        userDto.setSocialId(socialId);
        userDto.setSocialType(socialType);
        LoginResponse loginResponse = userTestController.login(userDto);
        assertThat(userRepository.findBySocialIdAndSocialType("kakao1", SocialType.KAKAO)
                .filter(it -> it.getStatus() == UserStatusType.ACTIVE)).isPresent();
        return loginResponse;
    }
}
