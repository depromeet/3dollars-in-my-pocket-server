package com.depromeet.team5.dto;

import lombok.Data;

@Data
public class LoginDto {
    private String token;
    private Long userId;
}