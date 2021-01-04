package com.depromeet.team5.integration.api;

import com.depromeet.team5.domain.user.SocialTypes;
import com.depromeet.team5.domain.user.User;
import com.depromeet.team5.dto.LoginResponse;
import com.depromeet.team5.dto.UserDto;
import com.depromeet.team5.dto.UserResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class UserTestController {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    public UserTestController(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    public LoginResponse login(UserDto userDto) throws Exception {
        MockHttpServletRequestBuilder builder = post("/api/v1/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userDto));
        return objectMapper.readValue(
                mockMvc.perform(builder)
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                LoginResponse.class
        );
    }

    public LoginResponse createTestUser() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setSocialId("socialId");
        userDto.setSocialType(SocialTypes.KAKAO);
        return login(userDto);
    }

    public UserResponse getMe(String accessToken) throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/user/me")
                .header("Authorization", accessToken))
                .andReturn();
        return objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(), UserResponse.class);
    }

    public User userInfo(String accessToken, Long userId) throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/user/info")
                .header("Authorization", accessToken)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("userId", userId.toString()))
                .andReturn();
        return objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(), User.class);
    }

    public void setNickname(String accessToken, Long userId, String nickname) throws Exception {
        mockMvc.perform(put("/api/v1/user/nickname")
                .header("Authorization", accessToken)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("userId", userId.toString())
                .param("nickName", nickname))
                .andReturn();
    }

    public void signout(String token) throws Exception {
        mockMvc.perform(post("/api/v1/user/signout")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON));
    }
}
