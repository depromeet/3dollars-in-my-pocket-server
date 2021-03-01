package com.depromeet.team5.dto;

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
    private List<String> categories;
    private Float rating;
    private Integer distance;
    private Double latitude;
    private Double longitude;
    @JsonProperty("images")
    private List<ImageResponse> imageResponses;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
