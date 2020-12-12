package com.depromeet.team5.count_user;

import java.text.MessageFormat;
import java.util.Arrays;

public enum CountUserStrategyType {
    DAILY(DailyUserStrategy.class),
    ALL(AllUserStrategy.class),
    DAILY_WITHDRAWAL(DailyWithdrawalUserStrategy.class),
    ;

    private final Class<? extends CountUserStrategy> strategyClass;

    CountUserStrategyType(Class<? extends CountUserStrategy> strategyClass) {
        this.strategyClass = strategyClass;
    }

    public static CountUserStrategyType from(String name) {
        return Arrays.stream(values())
                .filter(it -> it.name().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format("{0} is not supported type", name)));
    }
}
