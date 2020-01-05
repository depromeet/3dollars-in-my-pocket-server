package com.depromeet.team5.dto;

import com.depromeet.team5.domain.CategoryTypes;
import lombok.Data;

@Data
public class ReviewDto {

    private CategoryTypes category;

    private String contents;

    private Integer rating;
}
