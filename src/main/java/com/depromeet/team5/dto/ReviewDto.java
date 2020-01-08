package com.depromeet.team5.dto;

import com.depromeet.team5.domain.CategoryTypes;
import com.depromeet.team5.domain.Review;
import lombok.Data;

@Data
public class ReviewDto {

    private CategoryTypes category;

    private String contents;

    private Integer rating;

    public static ReviewDto from(Review review) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.category = review.getCategory();
        reviewDto.contents = review.getContents();
        reviewDto.rating = review.getRating();
        return reviewDto;
    }
}
