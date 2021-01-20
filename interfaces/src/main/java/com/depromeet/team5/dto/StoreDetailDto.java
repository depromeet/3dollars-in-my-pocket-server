package com.depromeet.team5.dto;

import com.depromeet.team5.domain.store.CategoryType;
import com.depromeet.team5.domain.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class StoreDetailDto {

    private Long id;

    private Double latitude;

    private Double longitude;

    private String storeName;

    private CategoryType category;

    private List<ImageDto> image;

    @JsonProperty("menu")
    private List<MenuResponse> menuResponses = new ArrayList<>();

    @JsonProperty("review")
    private List<ReviewDetailResponse> reviewDetailResponses = new ArrayList<>();

    private Float rating;

    private Integer distance;

    private User user;
}
