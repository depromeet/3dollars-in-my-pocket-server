package com.depromeet.team5.integration.api;

import com.depromeet.team5.dto.LoginDto;
import com.depromeet.team5.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class UserTestController {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    public UserTestController(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    public LoginDto login(UserDto userDto) throws Exception {
        MockHttpServletRequestBuilder builder = post("/api/v1/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userDto));
        return objectMapper.readValue(
                mockMvc.perform(builder)
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                LoginDto.class
        );
    }

    public void signout(String token) throws Exception {
        mockMvc.perform(post("/api/v1/user/signout")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON));
    }
}
