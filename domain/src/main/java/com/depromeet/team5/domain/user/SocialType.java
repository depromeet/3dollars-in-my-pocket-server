package com.depromeet.team5.domain.user;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SocialType {
    KAKAO("카카오"),
    GOOGLE("구글"),
    APPLE("애플");

    private final String type;
}
