package com.depromeet.team5.dto;

import lombok.Data;

@Data
public class LoginResponse {

    private String token;

    private Long userId;

    private Boolean state;
}