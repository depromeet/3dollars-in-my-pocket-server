package com.depromeet.team5.integration;

import com.depromeet.team5.Team5InterfacesApplication;
import com.depromeet.team5.dto.*;
import com.depromeet.team5.integration.api.ReviewTestController;
import com.depromeet.team5.integration.api.StoreTestController;
import com.depromeet.team5.integration.api.UserTestController;
import com.depromeet.team5.repository.ReviewRepository;
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

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest(classes = Team5InterfacesApplication.class)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class ReviewTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ReviewRepository reviewRepository;

    private ReviewTestController reviewTestController;

    private String accessToken;
    private Long userId;
    private Long storeId;

    @BeforeEach
    void setUp() throws Exception {
        reviewTestController = new ReviewTestController(mockMvc, objectMapper);

        LoginResponse loginResponse = new UserTestController(mockMvc, objectMapper).createTestUser();
        this.accessToken = loginResponse.getToken();
        this.userId = loginResponse.getUserId();
        StoreIdDto storeIdDto = new StoreTestController(mockMvc, objectMapper).createStore(accessToken, loginResponse.getUserId());
        this.storeId = storeIdDto.getStoreId();
    }

    @Test
    void save() throws Exception {
        // given
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setContents("reviewContent");
        reviewDto.setRating(5);
        // when
        reviewTestController.save(accessToken, userId, storeId, reviewDto);
        // then
        ReviewPomDto reviewPomDto = reviewTestController.getAllByUser(accessToken, userId, 1);
        assertThat(reviewPomDto.getContent()).hasSize(1);
        assertThat(reviewPomDto.getContent().get(0).getUser().getId()).isEqualTo(userId);
        assertThat(reviewPomDto.getContent().get(0).getStoreId()).isEqualTo(storeId);
    }

    @Test
    void update() throws Exception {
        // given
        reviewTestController.createReview(accessToken, userId, storeId);
        ReviewPomDto reviewPomDto = reviewTestController.getAllByUser(accessToken, userId, 1);
        Long reviewId = reviewPomDto.getContent().get(0).getUser().getId();
        ReviewUpdateRequest reviewUpdateRequest = new ReviewUpdateRequest();
        reviewUpdateRequest.setContents("updatedReviewContent");
        reviewUpdateRequest.setRating(0);
        // when
        ReviewResponse reviewResponse = reviewTestController.update(accessToken, reviewId, reviewUpdateRequest);
        // then
        assertThat(reviewResponse.getContent()).isEqualTo("updatedReviewContent");
        assertThat(reviewResponse.getRating()).isZero();
    }

    @Test
    void delete() throws Exception {
        // given
        reviewTestController.createReview(accessToken, userId, storeId);
        ReviewPomDto reviewPomDto = reviewTestController.getAllByUser(accessToken, userId, 1);
        Long reviewId = reviewPomDto.getContent().get(0).getUser().getId();
        // when
        reviewTestController.delete(accessToken, reviewId);
        // then
        ReviewPomDto reviewPomDtoAfterDeleted = reviewTestController.getAllByUser(accessToken, userId, 1);
        assertThat(reviewPomDtoAfterDeleted.getContent()).isEmpty();
    }
}
