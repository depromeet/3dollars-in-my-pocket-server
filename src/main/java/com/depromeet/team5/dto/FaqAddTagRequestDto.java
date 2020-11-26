package com.depromeet.team5.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
public class FaqAddTagRequestDto {
    @NotEmpty
    @Length(max = 255)
    private String tagName;
}
