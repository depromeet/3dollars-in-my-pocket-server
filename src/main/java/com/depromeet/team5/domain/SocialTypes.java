package com.depromeet.team5.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum  SocialTypes {
    KAKAO("카카오"),
    GOOGLE("구글"),
    APPLE("애플");

    private final String type;
}
