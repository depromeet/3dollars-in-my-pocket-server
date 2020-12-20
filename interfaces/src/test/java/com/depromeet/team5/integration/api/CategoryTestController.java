package com.depromeet.team5.integration.api;

import com.depromeet.team5.dto.StoresGroupByDistanceDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class CategoryTestController {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    public CategoryTestController(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    public StoresGroupByDistanceDto getDistanceAll(String accessToken, Double latitude, Double longitude, String category) throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/category/distance")
                .header("Authorization", accessToken)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("latitude", latitude.toString())
                .param("longitude", longitude.toString())
                .param("category", category))
                .andReturn();
        return objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(), StoresGroupByDistanceDto.class);
    }
}
