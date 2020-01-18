package com.depromeet.team5.domain;

import com.depromeet.team5.dto.ReviewDto;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @CreatedDate
    private LocalDateTime createdAt;

    public static Review from(ReviewDto reviewDto, User user, Long storeId) {
        Review review = new Review();
        review.storeId = storeId;
        review.contents = reviewDto.getContents();
        review.rating = reviewDto.getRating();
        review.user = user;
        return review;
    }
}
