package com.depromeet.team5.integration.api;

import com.depromeet.team5.domain.store.CategoryTypes;
import com.depromeet.team5.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StoreTestController {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    public StoreTestController(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    public StoreIdDto save(String accessToken,
                           Long userId,
                           StoreDto storeDto) throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/store/save")
                .header("Authorization", accessToken)
                .param("userId", userId.toString())
                .param("latitude", storeDto.getLatitude().toString())
                .param("longitude", storeDto.getLongitude().toString())
                .param("storeName", storeDto.getStoreName())
                .param("category", storeDto.getCategory().name())
        ).andReturn();
        return objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(), StoreIdDto.class);
    }

    public StoreIdDto createStore(String accessToken, Long userId) throws Exception {
        StoreDto storeDto = new StoreDto();
        storeDto.setStoreName("storeName");
        storeDto.setLatitude(37.0);
        storeDto.setLongitude(127.0);
        storeDto.setCategory(CategoryTypes.BUNGEOPPANG);
        MenuDto menuDto = new MenuDto();
        menuDto.setName("menuName");
        menuDto.setPrice("menuPrice");
        storeDto.setMenu(Collections.singletonList(menuDto));
        return this.save(accessToken, userId, storeDto);
    }

    public StoreDetailDto detail(String accessToken, Long storeId, Double latitude, Double longitude) throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/store/detail")
                .header("Authorization", accessToken)
                .queryParam("storeId", storeId.toString())
                .queryParam("latitude", latitude.toString())
                .queryParam("longitude", longitude.toString()))
                .andReturn();
        return objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(), StoreDetailDto.class);
    }

    public void saveImage(String accessToken, ImageRequestDto imageRequestDto) throws Exception {
        mockMvc.perform(post("/api/v1/store/image")
                .header("Authorization", accessToken)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("storeId", imageRequestDto.getStoreId().toString())
                .param("image", imageRequestDto.getImage().toString()))
                .andExpect(status().isOk());
    }

    public void deleteImage(String accessToken, Long imageId) throws Exception{
        mockMvc.perform(delete("/api/v1/store/image")
                .header("Authorization", accessToken)
                .queryParam("imageId", imageId.toString()))
                .andExpect(status().isOk());
    }
}
