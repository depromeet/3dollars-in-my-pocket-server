package com.depromeet.team5.integration.api;

import com.depromeet.team5.domain.store.CategoryType;
import com.depromeet.team5.dto.*;
import com.depromeet.team5.domain.store.PaymentMethodType;
import com.depromeet.team5.domain.store.StoreType;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;

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
                           StoreDto storeDto,
                           List<MultipartFile> image) throws Exception {
        MockHttpServletRequestBuilder builder = post("/api/v1/store/save")
                .header("Authorization", accessToken)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("userId", userId.toString())
                .param("latitude", storeDto.getLatitude().toString())
                .param("longitude", storeDto.getLongitude().toString())
                .param("storeName", storeDto.getStoreName());
        if (storeDto.getCategory() != null) {
            builder = builder.param("category", storeDto.getCategory().name());
        }
        if (storeDto.getCategories() != null) {
            String categoriesString = storeDto.getCategories()
                    .stream()
                    .map(Enum::name)
                    .collect(Collectors.joining(","));
            builder = builder.param("categories", categoriesString);
        }
        MvcResult mvcResult = mockMvc.perform(builder).andReturn();
        return objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(), StoreIdDto.class);
    }

    public StoreIdDto createStore(String accessToken, Long userId) throws Exception {
        StoreDto storeDto = new StoreDto();
        storeDto.setStoreName("storeName");
        storeDto.setStoreType(StoreType.ROAD);
        storeDto.setAppearanceDays(new HashSet<>(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.FRIDAY)));
        storeDto.setPaymentMethods(new HashSet<>(Arrays.asList(PaymentMethodType.CASH, PaymentMethodType.ACCOUNT_TRANSFER)));
        storeDto.setLatitude(37.0);
        storeDto.setLongitude(127.0);
        storeDto.setCategory(CategoryType.BUNGEOPPANG);
        MenuRequest menuRequest = new MenuRequest();
        menuRequest.setName("menuName");
        menuRequest.setPrice("menuPrice");
        storeDto.setMenu(Collections.singletonList(menuRequest));
        storeDto.setImage(Collections.emptyList());
        return this.save(accessToken, userId, storeDto, Collections.emptyList());
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

    public List<StoreResponse> getStoresByLocation(String accessToken, Double latitude, Double longitude, String category) throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/store")
                .header("Authorization", accessToken)
                .param("latitude", latitude.toString())
                .param("longitude", longitude.toString())
                .param("category", category))
                .andReturn();
        return objectMapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(),
                new TypeReference<List<StoreResponse>>() {}
        );
    }

    public StoreMyPagePomDto getAllByUser(String accessToken, Double latitude, Double longitude, Integer page) throws Exception{
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/store/user")
                .header("Authorization", accessToken)
                .queryParam("latitude", latitude.toString())
                .queryParam("longitude", longitude.toString())
                .queryParam("page", page.toString()))
                .andReturn();
        return objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(), StoreMyPagePomDto.class);
    }

    public StoreMyPagePomDto getAllByUser(String accessToken, Integer page) throws Exception{
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/store/user")
                .header("Authorization", accessToken)
                .queryParam("page", page.toString()))
                .andReturn();
        return objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(), StoreMyPagePomDto.class);
    }


    public void deleteImage(String accessToken, Long imageId) throws Exception {
        mockMvc.perform(delete("/api/v1/store/image")
                .header("Authorization", accessToken)
                .queryParam("imageId", imageId.toString()))
                .andExpect(status().isOk());
    }
}
