package com.depromeet.team5.util;

import lombok.Getter;

@Getter
public enum GoogleProviders {

    GOOGLE("GOOGLE", "https://www.googleapis.com/auth/userinfo.profile");

    private String name;
    private String userinfoEndpoint;

    GoogleProviders(String name, String userinfoEndpoint) {
        this.name = name;
        this.userinfoEndpoint = userinfoEndpoint;
    }
}
