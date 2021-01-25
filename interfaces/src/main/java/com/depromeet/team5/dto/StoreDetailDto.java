package com.depromeet.team5.dto;

import com.depromeet.team5.domain.store.CategoryTypes;
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

    private StoreType storeType;

    private Set<DayOfWeek> appearanceDays;

    private Set<PaymentMethodType> paymentMethods;

    private CategoryTypes category;

    private List<ImageDto> image;

    private List<MenuDto> menu = new ArrayList<>();

    @JsonProperty("review")
    private List<ReviewDetailResponse> reviewDetailResponses = new ArrayList<>();

    private Float rating;

    private Integer distance;

    private User user;
}
