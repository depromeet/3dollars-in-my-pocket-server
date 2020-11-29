package com.depromeet.team5.service;

public interface UserService {
    void signout(Long userId);

    void kakaoDeregister(String header, String userId, String referrerType);
}
