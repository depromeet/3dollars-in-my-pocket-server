package com.depromeet.team5.domain.store;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StoreType {
    ROAD("길거리"),
    STORE("매장"),
    CONVENIENCE_STORE("편의점");

    private final String type;
}
