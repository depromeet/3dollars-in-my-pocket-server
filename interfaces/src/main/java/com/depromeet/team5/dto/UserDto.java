package com.depromeet.team5.dto;

import com.depromeet.team5.domain.user.SocialType;
import lombok.Data;

@Data
public class UserDto {

    private String socialId;

    private String name;

    private SocialType socialType;

    private String token;
}
