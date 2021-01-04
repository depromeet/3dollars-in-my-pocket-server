package com.depromeet.team5.dto;

import com.depromeet.team5.domain.user.SocialTypes;
import lombok.Data;

@Data
public class UserDto {

    private String socialId;

    private String name;

    private SocialTypes socialType;

    private String token;
}
