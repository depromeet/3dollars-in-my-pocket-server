package com.depromeet.team5.dto;

import com.depromeet.team5.domain.CategoryTypes;
import com.depromeet.team5.domain.Review;
import lombok.Data;

@Data
public class ReviewDto {

    private CategoryTypes categoryTypes;

    private String contents;

    private Integer rating;

    public static ReviewDto from(Review review) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.categoryTypes = review.getCategoryTypes();
        reviewDto.contents = review.getContents();
        reviewDto.rating = review.getRating();
        return reviewDto;
    }
}
