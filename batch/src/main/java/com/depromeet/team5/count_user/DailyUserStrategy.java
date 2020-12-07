package com.depromeet.team5.count_user;

import com.depromeet.team5.service.UserService;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

/**
 * Count users who joined at targetDate.
 */
@Slf4j
public class DailyUserStrategy implements CountUserStrategy {
    private final UserService userService;
    private final LocalDate localDate;

    public DailyUserStrategy(UserService userService, LocalDate localDate) {
        this.userService = userService;
        this.localDate = localDate;
    }

    @Override
    public long countUsers() {
        return userService.countUserByCreatedDateEqualTo(this.localDate);
    }
}
