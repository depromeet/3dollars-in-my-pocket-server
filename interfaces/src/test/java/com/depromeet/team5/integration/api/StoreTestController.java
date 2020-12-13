package com.depromeet.team5.integration.api;

import com.depromeet.team5.dto.StoreDto;
import com.depromeet.team5.dto.StoreIdDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class StoreTestController {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    public StoreTestController(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    public StoreIdDto save(String accessToken,
                           Long userId,
                           StoreDto storeDto,
                           List<MultipartFile> image) throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/store/save")
                .header("Authorization", accessToken)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("userId", userId.toString())
                .param("latitude", storeDto.getLatitude().toString())
                .param("longitude", storeDto.getLongitude().toString())
                .param("storeName", storeDto.getStoreName())
                .param("category", storeDto.getCategory().name())
        ).andReturn();
        return objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(), StoreIdDto.class);
    }

}