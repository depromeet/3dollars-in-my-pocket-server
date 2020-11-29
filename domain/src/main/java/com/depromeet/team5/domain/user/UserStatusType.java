package com.depromeet.team5.domain.user;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserStatusType {
    ACTIVE("사용중인 사용자"),
    INACTIVE("탈퇴한 사용자");

    private final String status;
}