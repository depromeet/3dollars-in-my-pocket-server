package com.depromeet.team5.count_user;

import com.depromeet.team5.service.UserService;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Count all users who joined before targetDate.
 */
public class AllUserStrategy implements CountUserStrategy {
    private final UserService userService;
    private final LocalDate targetDate;

    public AllUserStrategy(UserService userService, LocalDate targetDate) {
        this.userService = userService;
        this.targetDate = targetDate;
    }

    @Override
    public long countUsers() {
        return userService.countByCreatedAtLessThan(targetDate.atTime(LocalTime.MIN).plusDays(1L));
    }
}
