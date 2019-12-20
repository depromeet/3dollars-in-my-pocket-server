package com.depromeet.team5.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class KakaoUserVo {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("properties")
    private Map<String, String> properties;

    public Long getUserId() {
        return this.id;
    }

    public String getUserName() {
        return this.properties.get("nickname");
    }

}