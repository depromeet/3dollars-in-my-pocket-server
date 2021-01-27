package com.depromeet.team5.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;

@Data
public class ReviewUpdateRequest {
    @NotBlank(message = "'contents' must not be blank")
    @Length(min = 1, max = 255)
    private String contents;
    @Range(min = 0, max = 5)
    private Integer rating;
}
