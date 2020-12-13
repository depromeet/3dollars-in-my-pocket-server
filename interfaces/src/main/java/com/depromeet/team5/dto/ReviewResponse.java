package com.depromeet.team5.dto;

import lombok.Data;

@Data
public class ReviewResponse {
    private Long id;
    private Long userId;
    private Long storeId;
    private String content;
    private Integer rating;
}
