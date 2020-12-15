package com.depromeet.team5.integration;

import com.depromeet.team5.Team5InterfacesApplication;
import com.depromeet.team5.domain.review.Review;
import com.depromeet.team5.domain.store.CategoryTypes;
import com.depromeet.team5.dto.*;
import com.depromeet.team5.integration.api.ReviewTestController;
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

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

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
        LoginDto loginDto = userTestController.createTestUser();
        StoreDto storeDto = new StoreDto();
        storeDto.setStoreName("storeName");
        storeDto.setLatitude(37.0);
        storeDto.setLongitude(127.0);
        storeDto.setCategory(CategoryTypes.BUNGEOPPANG);
        MenuDto menuDto = new MenuDto();
        menuDto.setName("menuName");
        menuDto.setPrice("menuPrice");
        storeDto.setMenu(Collections.singletonList(menuDto));
        storeDto.setImage(Collections.emptyList());
        // when
        StoreIdDto storeIdDto = storeTestController.save(loginDto.getToken(), loginDto.getUserId(), storeDto, Collections.emptyList());
        // then
        assertThat(storeIdDto.getStoreId()).isNotNull();
        StoreDetailDto storeDetailDto = storeTestController.detail(loginDto.getToken(), storeIdDto.getStoreId(), 37.1, 127.1);
        assertThat(storeDetailDto).isNotNull();
        assertThat(storeDetailDto.getId()).isEqualTo(storeIdDto.getStoreId());
        assertThat(storeDetailDto.getStoreName()).isEqualTo("storeName");
    }
}
