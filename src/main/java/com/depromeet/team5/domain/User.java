package com.depromeet.team5.domain;

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

    private String socialId;

    private String name;

    public static User from(SocialTypes socialType, String socialId, String name) {
        User user = new User();
        user.socialType = socialType;
        user.socialId = socialId;
        user.name = name;
        return user;
    }
}
