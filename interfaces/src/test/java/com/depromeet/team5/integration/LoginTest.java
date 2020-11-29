package com.depromeet.team5.integration;

import com.depromeet.team5.domain.user.SocialTypes;
import com.depromeet.team5.dto.UserDto;
import com.depromeet.team5.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("NonAsciiCharacters")
@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class LoginTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;

    @Test
    void login_카카오_로그인_처음_가입하는_경우() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setSocialId("socialIdForKakao");
        userDto.setSocialType(SocialTypes.KAKAO);

        mockMvc.perform(post("/api/v1/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userDto)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.token").exists())
               .andExpect(jsonPath("$.userId").exists())
               .andExpect(jsonPath("$.state").value(Boolean.FALSE))
               .andReturn();

        assertThat(userRepository.findBySocialIdAndSocialType("socialIdForKakao", SocialTypes.KAKAO)).isNotEmpty();
    }

    @Test
    void login_애플_로그인_처음_가입하는_경우() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setSocialId("socialIdForApple");
        userDto.setSocialType(SocialTypes.APPLE);

        mockMvc.perform(post("/api/v1/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userDto)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.token").exists())
               .andExpect(jsonPath("$.userId").exists())
               .andExpect(jsonPath("$.state").value(Boolean.FALSE))
               .andReturn();

        assertThat(userRepository.findBySocialIdAndSocialType("socialIdForApple", SocialTypes.APPLE)).isNotEmpty();
    }
}
