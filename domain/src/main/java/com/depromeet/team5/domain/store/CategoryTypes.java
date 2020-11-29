package com.depromeet.team5.domain.store;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum  CategoryTypes {
    BUNGEOPPANG("붕어빵"),
    TAKOYAKI("타코야끼"),
    GYERANPPANG("계란빵"),
    HOTTEOK("호떡");

    private final String category;
}
