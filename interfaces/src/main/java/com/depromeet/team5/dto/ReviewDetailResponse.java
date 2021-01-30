package com.depromeet.team5.dto;

import com.depromeet.team5.domain.store.CategoryType;
import com.depromeet.team5.domain.user.User;
import lombok.Data;

import java.time.LocalDateTime;

// TODO: 앱 crash 해결하면 status 필드 추가해야함
@Data
public class ReviewDetailResponse {
    private Long id;
    private Long storeId;
    private String storeName;
    private CategoryType category;
    private String contents;
    private Integer rating;
    private User user;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
