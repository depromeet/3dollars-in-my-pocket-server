package com.depromeet.team5.integration;

import com.depromeet.team5.Team5InterfacesApplication;
import com.depromeet.team5.domain.store.CategoryType;
import com.depromeet.team5.domain.store.PaymentMethodType;
import com.depromeet.team5.domain.store.StoreType;
import com.depromeet.team5.dto.LoginResponse;
import com.depromeet.team5.dto.StoreDto;
import com.depromeet.team5.dto.StoreIdDto;
import com.depromeet.team5.integration.api.StoreTestController;
import com.depromeet.team5.integration.api.UserTestController;
import com.depromeet.team5.repository.StoreMenuCategoryRepository;
import com.depromeet.team5.repository.StoreRepository;
import com.depromeet.team5.service.MenuCategoryService;
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

@Transactional
@SpringBootTest(classes = Team5InterfacesApplication.class)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class StoreCategoryTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private StoreMenuCategoryRepository storeMenuCategoryRepository;
    @Autowired
    private MenuCategoryService menuCategoryService;
    @Autowired
    private StoreRepository storeRepository;

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
        menuCategoryService.create(CategoryType.BUNGEOPPANG.name());
        // when
        StoreDto storeDto = new StoreDto();
        storeDto.setStoreName("storeName");
        storeDto.setCategoryType(CategoryType.BUNGEOPPANG);
        storeDto.setStoreType(StoreType.ROAD);
        storeDto.setAppearanceDays(new HashSet<>(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.FRIDAY)));
        storeDto.setPaymentMethods(new HashSet<>(Arrays.asList(PaymentMethodType.CASH, PaymentMethodType.ACCOUNT_TRANSFER)));
        storeDto.setCategoryType(CategoryType.BUNGEOPPANG);
        storeDto.setLatitude(37.0);
        storeDto.setLongitude(127.0);
        storeDto.setMenu(Collections.emptyList());
        storeDto.setImage(Collections.emptyList());
        StoreIdDto storeIdDto = storeTestController.save(
                loginResponse.getToken(),
                loginResponse.getUserId(),
                storeDto,
                Collections.emptyList()
        );
        // then
        assertThat(storeRepository.findAll()).hasSize(1);
        assertThat(storeMenuCategoryRepository.findAll()).hasSize(1);
    }
}
