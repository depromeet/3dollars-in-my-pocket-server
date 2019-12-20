package com.depromeet.team5.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GoogleUserVo {
    @JsonProperty("iss")
    private Long id;
    @JsonProperty("name")
    private String name;

    public Long getUserId() {
        return this.id;
    }

    public String getUserName() {
        return this.name;
    }

}