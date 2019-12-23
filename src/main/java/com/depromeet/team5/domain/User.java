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

    private Long socialId;

    private String name;
}
