package com.depromeet.team5.integration;

import com.depromeet.team5.Team5InterfacesApplication;
import com.depromeet.team5.domain.user.User;
import com.depromeet.team5.dto.LoginResponse;
import com.depromeet.team5.dto.UserResponse;
import com.depromeet.team5.integration.api.UserTestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest(classes = Team5InterfacesApplication.class)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class UserTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private UserTestController userTestController;

    @BeforeEach
    void setUp() {
        userTestController = new UserTestController(mockMvc, objectMapper);
    }

    @Test
    void getMe() throws Exception {
        // given
        LoginResponse loginResponse = userTestController.createTestUser();
        String accessToken = loginResponse.getToken();
        userTestController.setNickname(accessToken, loginResponse.getUserId(), "nickname");
        // when
        UserResponse userResponse = userTestController.getMe(accessToken);
        // then
        assertThat(userResponse.getUserId()).isEqualTo(loginResponse.getUserId());
        assertThat(userResponse.getName()).isEqualTo("nickname");
    }

    @Test
    void setNicknameTest0() throws Exception {
        // given
        LoginResponse loginResponse = userTestController.createTestUser();
        String accessToken = loginResponse.getToken();
        Long userId = loginResponse.getUserId();
        // when
        userTestController.setNickname(accessToken, userId, "nickname");
        // then
        User user = userTestController.userInfo(accessToken, userId);
        assertThat(user.getName()).isEqualTo("nickname");
    }

    @DisplayName("nickname 에 빈 문자열을 입력하면 400 으로 응답해야함")
    @Test
    void setNicknameTest1() throws Exception {
        // given
        LoginResponse loginResponse = userTestController.createTestUser();
        String accessToken = loginResponse.getToken();
        Long userId = loginResponse.getUserId();
        // when
        mockMvc.perform(put("/api/v1/user/nickname")
                .header("Authorization", accessToken)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("userId", userId.toString())
                .param("nickName", ""))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("nickname 에 빈 문자열을 입력하면 400 으로 응답해야함")
    @Test
    void setNicknameTest2() throws Exception {
        // given
        LoginResponse loginResponse = userTestController.createTestUser();
        String accessToken = loginResponse.getToken();
        Long userId = loginResponse.getUserId();
        // when
        mockMvc.perform(put("/api/v1/user/nickname")
                .header("Authorization", accessToken)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("userId", userId.toString())
                .param("nickName", " \n"))
                .andExpect(status().isBadRequest());
    }
}
