package com.depromeet.team5.dto;

import lombok.Data;

@Data
public class FaqRemoveTagRequestDto {
    private Long tagId;
    private String tagName;
}
