package com.depromeet.team5.service;

import com.depromeet.team5.domain.user.User;
import com.depromeet.team5.domain.user.UserStatusType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface UserService {
    User getActiveUser(Long userId);

    void signout(Long userId);

    void kakaoDeregister(String header, String userId, String referrerType);

    long countUserByCreatedDateEqualTo(LocalDate localDate);

    long countByCreatedAtLessThan(LocalDateTime localDateTime);

    long countByUpdatedDateEqualToAndStatus(LocalDate localDate, UserStatusType userStatusType);
}
