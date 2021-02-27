package com.depromeet.team5.dto;

import com.depromeet.team5.domain.store.CategoryType;
import com.depromeet.team5.domain.store.Image;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class StoreResponse {
    private Long id;
    @JsonProperty("user")
    private UserResponse userResponse;
    private String storeName;
    private String category;
    private List<CategoryType> categories;
    private Float rating;
    private Integer distance;
    private Double latitude;
    private Double longitude;
    private List<Image> images;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
