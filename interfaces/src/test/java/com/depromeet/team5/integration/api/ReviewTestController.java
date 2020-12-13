package com.depromeet.team5.integration.api;

import com.depromeet.team5.dto.ReviewDto;
import com.depromeet.team5.dto.ReviewPomDto;
import com.depromeet.team5.dto.ReviewResponse;
import com.depromeet.team5.dto.ReviewUpdateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReviewTestController {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    public ReviewTestController(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    public String save(String accessToken, Long userId, Long storeId, ReviewDto reviewDto) throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/review")
                .header("Authorization", accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("userId", userId.toString())
                .queryParam("storeId", storeId.toString())
                .content(objectMapper.writeValueAsBytes(reviewDto)))
                .andReturn();
        return objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(), String.class);
    }

    public ReviewPomDto getAllByUser(String accessToken, Long userId, Integer page) throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/review/user")
                .header("Authorization", accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("userId", userId.toString())
                .queryParam("page", page.toString()))
                .andReturn();
        return objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(), ReviewPomDto.class);
    }

    public ReviewResponse update(String accessToken, Long reviewId, ReviewUpdateRequest reviewUpdateRequest) throws Exception {
        MvcResult mvcResult = mockMvc.perform(put("/api/v1/review/{reviewId}", reviewId)
                .header("Authorization", accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(reviewUpdateRequest)))
                .andReturn();
        return objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(), ReviewResponse.class);
    }

    public void delete(String accessToken, Long reviewId) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/review/{reviewId}", reviewId)
                .header("Authorization", accessToken))
                .andExpect(status().isNoContent());
    }
}
