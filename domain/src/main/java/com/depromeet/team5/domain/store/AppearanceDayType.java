package com.depromeet.team5.domain.store;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AppearanceDayType {
    MONDAY("월"),
    TUESDAY("화"),
    WEDNESDAY("수"),
    THURSDAY("목"),
    FRIDAY("금"),
    SATURDAY("토"),
    SUNDAY("일");

    private final String type;
}
