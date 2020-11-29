package com.depromeet.team5.integration.api;

import com.depromeet.team5.dto.FaqRequestDto;
import com.depromeet.team5.dto.FaqResponseDto;
import com.depromeet.team5.dto.FaqUpdateRequestDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


public class FaqTestController {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    public FaqTestController(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    public FaqResponseDto create(String token, FaqRequestDto faqRequestDto) throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/faqs")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(faqRequestDto)))
                .andReturn();
        return objectMapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(),
                FaqResponseDto.class
        );
    }

    public List<FaqResponseDto> getFaqs(String token, List<Long> tagIds) throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/faqs")
                .header("Authorization", token)
                .queryParam("tagIds", tagIds.stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(","))))
                .andReturn();
        return objectMapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(),
                new TypeReference<List<FaqResponseDto>>() {}
        );
    }

    public FaqResponseDto getFaq(String token, Long faqId) throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/faqs/{faqId}", faqId)
                .header("Authorization", token))
                .andReturn();
        return objectMapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(),
                FaqResponseDto.class
        );
    }

    public FaqResponseDto update(String token, Long faqId, FaqUpdateRequestDto faqUpdateRequestDto) throws Exception {
        MvcResult mvcResult = mockMvc.perform(put("/api/v1/faqs/{faqId}", faqId)
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(faqUpdateRequestDto)))
                .andReturn();
        return objectMapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(),
                FaqResponseDto.class
        );
    }

    public void deleteFaq(String token, Long faqId) throws Exception {
        mockMvc.perform(delete("/api/v1/faqs/{faqId}", faqId)
                .header("Authorization", token));
    }
}
