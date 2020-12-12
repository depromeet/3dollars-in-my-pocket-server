package com.depromeet.team5.dto;

import com.depromeet.team5.domain.store.Review;
import lombok.Data;

@Data
public class ReviewDto {

    private String contents;

    private Integer rating;

    public static ReviewDto from(Review review) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.contents = review.getContents();
        reviewDto.rating = review.getRating();
        return reviewDto;
    }
}
