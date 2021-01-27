package com.depromeet.team5.integration;

import com.depromeet.team5.Team5InterfacesApplication;
import com.depromeet.team5.domain.store.CategoryTypes;
import com.depromeet.team5.domain.store.PaymentMethodType;
import com.depromeet.team5.domain.store.StoreType;
import com.depromeet.team5.dto.*;
import com.depromeet.team5.integration.api.StoreTestController;
import com.depromeet.team5.integration.api.UserTestController;
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

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Transactional
@SpringBootTest(classes = Team5InterfacesApplication.class)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class StoreTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private UserTestController userTestController;
    private StoreTestController storeTestController;

    @BeforeEach
    void setUp() {
        userTestController = new UserTestController(mockMvc, objectMapper);
        storeTestController = new StoreTestController(mockMvc, objectMapper);
    }

    @Test
    void save() throws Exception {
        // given
        LoginResponse loginResponse = userTestController.createTestUser();
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
        // when
        StoreIdDto storeIdDto = storeTestController.save(loginResponse.getToken(), loginResponse.getUserId(), storeDto, Collections.emptyList());
        // then
        assertThat(storeIdDto.getStoreId()).isNotNull();
        StoreDetailDto storeDetailDto = storeTestController.detail(loginResponse.getToken(), storeIdDto.getStoreId(), 37.1, 127.1);
        assertThat(storeDetailDto).isNotNull();
        assertThat(storeDetailDto.getId()).isEqualTo(storeIdDto.getStoreId());
        assertThat(storeDetailDto.getStoreName()).isEqualTo("storeName");
    }

    @Test
    void get_given_invalid_latitude_should_return_400_bad_request() throws Exception{
        //given
        LoginResponse loginResponse = userTestController.createTestUser();
        //when
        mockMvc.perform(get("/api/v1/store/get?latitude=37.1%26longitude=127.1")
                .header("Authorization", loginResponse.getToken()))
                //then
                .andExpect(status().isBadRequest());
    }
}
