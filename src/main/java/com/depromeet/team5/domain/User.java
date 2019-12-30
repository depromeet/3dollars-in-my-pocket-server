package com.depromeet.team5.domain;

import com.depromeet.team5.dto.UserDto;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private SocialTypes socialType;

    private Long socialId;

    private String name;

    public static User from(UserDto userDto) {
        User user = new User();
        user.socialType = userDto.getSocialType();
        user.socialId = userDto.getSocialId();
        user.name = userDto.getName();
        return user;
    }
}
