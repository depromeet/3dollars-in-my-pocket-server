package com.depromeet.team5.domain.user;

import lombok.Value;

@Value(staticConstructor = "of")
public class SocialVo {
    String socialId;
    SocialType socialType;
    String token;
}
