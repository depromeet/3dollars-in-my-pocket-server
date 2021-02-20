package com.depromeet.team5.dto;

import com.depromeet.team5.domain.store.CategoryType;
import com.depromeet.team5.domain.store.PaymentMethodType;
import com.depromeet.team5.domain.store.StoreType;
import com.depromeet.team5.domain.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
public class StoreDetailDto {

    private Long id;

    private Double latitude;

    private Double longitude;

    private String storeName;
    /**
     * 대표 카테고리
     */
    private CategoryType category;
    /**
     * 가게 카테고리 목록
     */
    private List<CategoryType> categories;

    private StoreType storeType;

    private Set<DayOfWeek> appearanceDays;

    private Set<PaymentMethodType> paymentMethods;

    /**
     * 이미지. 생성일 역순으로 정렬
     */
    @JsonProperty("image")
    private List<ImageResponse> imageResponses;

    @JsonProperty("menu")
    private List<MenuResponse> menuResponses = new ArrayList<>();

    @JsonProperty("review")
    private List<ReviewDetailResponse> reviewDetailResponses = new ArrayList<>();

    private Float rating;

    private Integer distance;

    private User user;
}
