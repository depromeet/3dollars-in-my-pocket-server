package com.depromeet.team5.domain.store;

import com.depromeet.team5.domain.user.User;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
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

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public static Review from(ReviewCreateValue reviewCreateValue, User user, Store store) {
        Review review = new Review();
        review.storeId = store.getId();
        review.category = store.getCategory();
        review.storeName = store.getStoreName();
        review.contents = reviewCreateValue.getContents();
        review.rating = reviewCreateValue.getRating();
        review.user = user;
        return review;
    }
}
