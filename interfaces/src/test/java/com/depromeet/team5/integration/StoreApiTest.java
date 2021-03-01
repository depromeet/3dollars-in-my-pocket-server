package com.depromeet.team5.integration;

import com.depromeet.team5.Team5InterfacesApplication;
import com.depromeet.team5.domain.Location;
import com.depromeet.team5.domain.store.CategoryType;
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
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SuppressWarnings("NonAsciiCharacters")
@Transactional
@SpringBootTest(classes = Team5InterfacesApplication.class)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class StoreApiTest {
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
        storeDto.setCategory(CategoryType.BUNGEOPPANG);
        MenuRequest menuRequest = new MenuRequest();
        menuRequest.setCategory(CategoryType.BUNGEOPPANG);
        menuRequest.setName("menuName");
        menuRequest.setPrice("menuPrice");
        storeDto.setMenu(Collections.singletonList(menuRequest));
        storeDto.setImage(Collections.emptyList());
        // when
        StoreIdDto storeIdDto = storeTestController.save(loginResponse.getToken(), loginResponse.getUserId(), storeDto, Collections.emptyList());
        // then
        assertThat(storeIdDto.getStoreId()).isNotNull();
        StoreDetailDto storeDetailDto = storeTestController.detail(loginResponse.getToken(), storeIdDto.getStoreId(), 37.1, 127.1);
        assertThat(storeDetailDto).isNotNull();
        assertThat(storeDetailDto.getId()).isEqualTo(storeIdDto.getStoreId());
        assertThat(storeDetailDto.getStoreName()).isEqualTo("storeName");
        assertThat(storeDetailDto.getRating()).isZero();
    }

    @Test
    void getAllByUser_내가_등록한_가게가_없는_경우() throws Exception {
        // given
        LoginResponse loginResponse = userTestController.createTestUser();
        double latitude = 37.0;
        double longitude = 127.0;
        // when
        StoreMyPagePomDto actual = storeTestController.getAllByUser(loginResponse.getToken(), latitude, longitude, 1);
        // then
        assertThat(actual.getTotalElements()).isZero();
    }

    @Test
    void getAllByUser_내가_등록한_가게가_있고_위치정보는_입력하지않은_경우() throws Exception {
        LoginResponse loginResponse = userTestController.createTestUser();
        this.createStores(loginResponse.getToken(), loginResponse.getUserId(), 1, Collections.emptyList());
        // when
        StoreMyPagePomDto actual = storeTestController.getAllByUser(loginResponse.getToken(), 1);
        // then
        assertThat(actual.getTotalElements()).isEqualTo(1);
        assertThat(actual.getContent()).allMatch(it -> it.getDistance() == null);
    }

    @Test
    void getAllByUser_내가_등록한_가게가_있고_위치정보도_입력한_경우() throws Exception {
        LoginResponse loginResponse = userTestController.createTestUser();
        this.createStores(loginResponse.getToken(), loginResponse.getUserId(), 2, Collections.emptyList());
        double latitude = 37.0;
        double longitude = 127.0;
        // when
        StoreMyPagePomDto actual = storeTestController.getAllByUser(loginResponse.getToken(), latitude, longitude, 1);
        // then
        assertThat(actual.getContent()).allMatch(it -> it.getDistance() != null);
    }

    @Test
    void getStoresByLocation_2km_이내에_가게가_없는_경우() throws Exception {
        // given
        LoginResponse loginResponse = userTestController.createTestUser();
        double latitude = 37.0;
        double longitude = 127.0;
        // when
        StoreMyPagePomDto actual = storeTestController.getAllByUser(loginResponse.getToken(), latitude, longitude, 1);
        // then
        assertThat(actual.getTotalElements()).isZero();
    }

    @Test
    void getStoresByLocation_2km_이내에_가게가_있는_경우() throws Exception {
        // given
        LoginResponse loginResponse = userTestController.createTestUser();
        List locationList = new ArrayList();
        locationList.add(new Location(37.111, 127.111));
        locationList.add(new Location(37.112, 127.112));
        locationList.add(new Location(37.113, 127.113));
        this.createStores(loginResponse.getToken(), loginResponse.getUserId(), 3, locationList);
        double latitude = 37.110;
        double longitude = 127.110;
        double mapLatitude = 37.111;
        double mapLongitude = 127.111;
        double distance = 2000;
        // when
        List<StoreResponse> actual = storeTestController.getStoresByLocation(loginResponse.getToken(), latitude, longitude, mapLatitude, mapLongitude, distance);
        // then
        assertThat(actual).hasSize(3);
        assertThat(actual).isSortedAccordingTo(Comparator.comparing(StoreResponse::getDistance));
    }

    @Test
    void get_given_invalid_latitude_should_return_400_bad_request() throws Exception {
        //given
        LoginResponse loginResponse = userTestController.createTestUser();
        //when
        mockMvc.perform(get("/api/v1/store/get?latitude=37.1%26longitude=127.1")
                .header("Authorization", loginResponse.getToken()))
                //then
                .andExpect(status().isBadRequest());
    }

    private List<StoreIdDto> createStores(String token, Long userId, int size, List<Location> locationList) {
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        return IntStream.range(1, size + 1)
                .mapToObj(it -> {
                    StoreDto storeDto = new StoreDto();
                    storeDto.setStoreName("storeName" + it);
                    storeDto.setStoreType(StoreType.ROAD);
                    if (locationList.isEmpty()) {
                        storeDto.setLatitude(37.0 + threadLocalRandom.nextDouble(1.0));
                        storeDto.setLongitude(127.0 + threadLocalRandom.nextDouble(1.0));
                    } else {
                        storeDto.setLatitude(locationList.get(it-1).getLatitude());
                        storeDto.setLongitude(locationList.get(it-1).getLongitude());
                    }
                    storeDto.setCategories(Collections.singletonList(
                            CategoryType.values()[threadLocalRandom.nextInt(0, CategoryType.values().length)]
                    ));
                    return storeDto;
                })
                .map(it -> {
                    try {
                        return storeTestController.save(token, userId, it, Collections.emptyList());
                    } catch (Exception e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
