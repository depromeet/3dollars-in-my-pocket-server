package com.depromeet.team5.integration.api;

import com.depromeet.team5.domain.store.CategoryTypes;
import com.depromeet.team5.domain.store.PaymentMethodType;
import com.depromeet.team5.domain.store.StoreType;
import com.depromeet.team5.dto.MenuDto;
import com.depromeet.team5.dto.StoreDetailDto;
import com.depromeet.team5.dto.StoreDto;
import com.depromeet.team5.dto.StoreIdDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.multipart.MultipartFile;

import java.time.DayOfWeek;
import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    public StoreIdDto createStore(String accessToken, Long userId) throws Exception {
        StoreDto storeDto = new StoreDto();
        storeDto.setStoreName("storeName");
        storeDto.setStoreType(StoreType.ROAD);
        storeDto.setAppearanceDays(new HashSet<>(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.FRIDAY)));
        storeDto.setPaymentMethods(new HashSet<>(Arrays.asList(PaymentMethodType.CASH, PaymentMethodType.ACCOUNT_TRANSFER)));
        storeDto.setLatitude(37.0);
        storeDto.setLongitude(127.0);
        storeDto.setCategory(CategoryTypes.BUNGEOPPANG);
        MenuDto menuDto = new MenuDto();
        menuDto.setName("menuName");
        menuDto.setPrice("menuPrice");
        storeDto.setMenu(Collections.singletonList(menuDto));
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
}
