package com.depromeet.team5.domain;

import com.depromeet.team5.dto.ReviewDto;
import com.depromeet.team5.dto.ReviewUpdateDto;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long storeId;

    private String contents;

    private Integer rating;

    @ManyToOne
    private User user;

    public static Review from(ReviewDto reviewDto, User user, Long storeId) {
        Review review = new Review();
        review.storeId = storeId;
        review.contents = reviewDto.getContents();
        review.rating = reviewDto.getRating();
        review.user = user;
        return review;
    }

    public void setReview(ReviewUpdateDto reviewUpdateDto) {
        contents = reviewUpdateDto.getContents();
        rating = reviewUpdateDto.getRating();
    }
}
