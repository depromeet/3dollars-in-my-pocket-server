package com.depromeet.team5.domain;

import com.depromeet.team5.dto.ReviewDto;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long storeId;

    private String storeName;

    @Enumerated(value = EnumType.STRING)
    private CategoryTypes category;

    private String contents;

    private Integer rating;

    @ManyToOne
    private User user;

    @CreatedDate
    private LocalDateTime createdAt;

    public static Review from(ReviewDto reviewDto, User user, Store store) {
        Review review = new Review();
        review.storeId = store.getId();
        review.category = store.getCategory();
        review.storeName = store.getStoreName();
        review.contents = reviewDto.getContents();
        review.rating = reviewDto.getRating();
        review.user = user;
        return review;
    }
}
