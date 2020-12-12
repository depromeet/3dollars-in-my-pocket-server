package com.depromeet.team5.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponse {
    private Long userId;
    private String name;
    private Boolean state;
    private String status;
    private String socialType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
