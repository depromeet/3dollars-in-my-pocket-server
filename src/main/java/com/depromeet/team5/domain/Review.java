package com.depromeet.team5.domain;

import com.depromeet.team5.dto.ReviewDto;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Review {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private CategoryTypes category;

    private String contents;

    private Integer rating;

    @ManyToOne
    private User user;

    public static Review from(ReviewDto reviewDto, User user) {
        Review review = new Review();
        review.category = reviewDto.getCategory();
        review.contents = reviewDto.getContents();
        review.rating = reviewDto.getRating();
        review.user = user;
        return review;
    }
}
