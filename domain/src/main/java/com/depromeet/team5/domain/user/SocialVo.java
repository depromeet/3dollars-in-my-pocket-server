package com.depromeet.team5.domain.user;

import com.depromeet.team5.domain.SocialTypes;
import lombok.Value;

@Value(staticConstructor = "of")
public class SocialVo {
    String socialId;
    SocialTypes socialType;
}
