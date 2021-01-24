package com.depromeet.team5.integration.api;

import com.depromeet.team5.dto.*;
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

    public void save(String accessToken, Long userId, Long storeId, ReviewDto reviewDto) throws Exception {
        mockMvc.perform(post("/api/v1/review/save")
                .header("Authorization", accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("userId", userId.toString())
                .queryParam("storeId", storeId.toString())
                .content(objectMapper.writeValueAsBytes(reviewDto)))
                .andReturn();
    }
    
    public void createReview(String accessToken, Long userId, Long storeId) throws Exception {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setContents("reviewContent");
        reviewDto.setRating(5);
        save(accessToken, userId, storeId, reviewDto);
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

    public MvcResult getDetailReview(String accessToken, Long reviewId) throws Exception {
        return mockMvc.perform(get("/api/v1/review/{reviewId}", reviewId)
                .header("Authorization", accessToken))
                .andReturn();
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
