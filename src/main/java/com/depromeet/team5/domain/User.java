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

    @Enumerated(value = EnumType.STRING)
    private UserStatusType status;

    public static User from(UserDto userDto) {
        User user = new User();
        user.socialType = userDto.getSocialType();
        user.socialId = userDto.getSocialId();
        user.name = userDto.getName();
        user.state = false;
        user.status = UserStatusType.ACTIVE;
        return user;
    }

    public void setName(String nickName) {
        name = nickName;
        state = true;
    }

    public String getName() {
        if (status == UserStatusType.INACTIVE)
            return "사라진 제보자";
        else
            return name;
    }

}
