package com.depromeet.team5.domain;

import com.depromeet.team5.dto.UserDto;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private SocialTypes socialType;

    private String socialId;

    private String name;

    private Boolean state;

    public static User from(UserDto userDto) {
        User user = new User();
        user.socialType = userDto.getSocialType();
        user.socialId = userDto.getSocialId();
        user.name = userDto.getName();
        user.state = false;
        return user;
    }

    public void setName(String nickName) {
        name = nickName;
        state = true;
    }

}
