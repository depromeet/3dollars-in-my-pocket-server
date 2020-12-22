package com.depromeet.team5.integration;

import com.depromeet.team5.Team5InterfacesApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("NonAsciiCharacters")
@Transactional
@SpringBootTest(classes = Team5InterfacesApplication.class)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class TokenTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getUserToken_개발환경에서_사용자_토큰_발급_성공() throws Exception {
        // given
        long userId = 1L;
        // when
        mockMvc.perform(get("/api/v1/user/{userId}/token", userId))
               // then
               .andExpect(status().isOk())
               .andExpect(jsonPath("$").value("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJpc3N1ZXJKd3QiLCJJRCI6MX0.y8f5y9Glws9uCwjj4FUf1XmN64nyZBypkrJBkfvDsf0"));
    }
}
