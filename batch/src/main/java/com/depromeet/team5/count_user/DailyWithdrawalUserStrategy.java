package com.depromeet.team5.count_user;

import com.depromeet.team5.domain.user.UserStatusType;
import com.depromeet.team5.service.UserService;

import java.time.LocalDate;

/**
 * Count users who withdraw at targetDate.
 */
public class DailyWithdrawalUserStrategy implements CountUserStrategy {
    private final UserService userService;
    private final LocalDate targetDate;

    public DailyWithdrawalUserStrategy(UserService userService, LocalDate targetDate) {
        this.userService = userService;
        this.targetDate = targetDate;
    }

    @Override
    public long countUsers() {
        return userService.countByUpdatedDateEqualToAndStatus(targetDate, UserStatusType.INACTIVE);
    }
}
