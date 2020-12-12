package com.depromeet.team5.integration;

import com.depromeet.team5.Team5InterfacesApplication;
import com.depromeet.team5.domain.user.SocialTypes;
import com.depromeet.team5.dto.*;
import com.depromeet.team5.integration.api.FaqTestController;
import com.depromeet.team5.integration.api.UserTestController;
import com.depromeet.team5.service.FaqService;
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

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest(classes = Team5InterfacesApplication.class)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class FaqTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private FaqService faqService;

    private FaqTestController faqTestController;
    private String token;

    @BeforeEach
    void setUp() throws Exception {
        faqTestController = new FaqTestController(mockMvc, objectMapper);
        UserDto userDto = new UserDto();
        userDto.setSocialId("kakao1");
        userDto.setSocialType(SocialTypes.KAKAO);
        token = new UserTestController(mockMvc, objectMapper).login(userDto).getToken();
    }

    @Test
    void create() throws Exception {
        // given
        String question = "question";
        String answer = "answer";
        FaqRequestDto faqRequestDto = new FaqRequestDto();
        faqRequestDto.setQuestion(question);
        faqRequestDto.setAnswer(answer);
        // when
        FaqResponseDto faqResponseDto = faqTestController.create(token, faqRequestDto);
        // then
        assertThat(faqResponseDto.getQuestion()).isEqualTo("question");
        assertThat(faqResponseDto.getAnswer()).isEqualTo("answer");
    }

    @Test
    void getFaqs() throws Exception {
        // given
        createFaq("q1", "a1");
        createFaq("q2", "a2");
        createFaq("q3", "a3");
        // when
        List<FaqResponseDto> faqResponseDtoList = faqTestController.getFaqs(token, Collections.emptyList());
        // then
        assertThat(faqResponseDtoList).hasSize(3);
    }

    @Test
    void getFaq() throws Exception {
        // given
        Long faqId = createFaq("q1", "a1").getId();
        // when
        FaqResponseDto faqResponseDto = faqTestController.getFaq(token, faqId);
        // then
        assertThat(faqResponseDto.getQuestion()).isEqualTo("q1");
        assertThat(faqResponseDto.getAnswer()).isEqualTo("a1");
    }

    @Test
    void update() throws Exception {
        // given
        Long faqId = createFaq("oldQuestion", "oldAnswer").getId();
        // when
        FaqUpdateRequestDto faqUpdateRequestDto = new FaqUpdateRequestDto();
        faqUpdateRequestDto.setQuestion("newQuestion");
        faqUpdateRequestDto.setAnswer("newAnswer");
        FaqResponseDto faqResponseDto = faqTestController.update(token, faqId, faqUpdateRequestDto);
        // then
        assertThat(faqResponseDto.getQuestion()).isEqualTo("newQuestion");
        assertThat(faqResponseDto.getAnswer()).isEqualTo("newAnswer");
    }

    @Test
    void delete() throws Exception {
        // given
        Long faqId = createFaq("q1", "a1").getId();
        // when
        faqTestController.deleteFaq(token, faqId);
        // then
        assertThat(faqService.getFaq(faqId)).isNotPresent();
    }

    private FaqResponseDto createFaq(String question, String answer) throws Exception {
        FaqRequestDto faqRequestDto = new FaqRequestDto();
        faqRequestDto.setQuestion(question);
        faqRequestDto.setAnswer(answer);
        return faqTestController.create(token, faqRequestDto);
    }
}
