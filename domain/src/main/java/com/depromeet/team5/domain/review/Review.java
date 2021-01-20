package com.depromeet.team5.domain.review;

import com.depromeet.team5.domain.store.CategoryType;
import com.depromeet.team5.domain.store.Store;
import com.depromeet.team5.domain.user.User;
import com.depromeet.team5.exception.ReviewModifiedByNotAuthorException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@EqualsAndHashCode(of = {"id", "storeId", "contents", "rating", "createdAt"})
@EntityListeners(AuditingEntityListener.class)
public class Review {
    private static final int RATING_MAX = 5;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long storeId;

    private String storeName;

    @Enumerated(value = EnumType.STRING)
    private CategoryType category;

    private String contents;

    private Integer rating;

    @Enumerated(EnumType.STRING)
    private ReviewStatus status;

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
        // TODO: 앱에서 validation 적용하고나면 setter 사용하기.
        review.contents = reviewCreateValue.getContent();
        review.rating = reviewCreateValue.getRating();
        review.status = ReviewStatus.POSTED;
        review.user = user;
        return review;
    }

    public Review update(User user, ReviewUpdateValue reviewUpdateValue) {
        this.validateUser(user);

        this.setContents(reviewUpdateValue.getContent());
        this.setRating(reviewUpdateValue.getRating());
        return this;
    }

    public Review delete(User user) {
        this.validateUser(user);

        this.setStatusDeleted();
        return this;
    }

    public boolean isVisible() {
        return status != null && status.isVisible();
    }

    private void validateUser(User user) {
        if (this.user != null && !this.user.equals(user)) {
            throw new ReviewModifiedByNotAuthorException();
        }
    }

    private void setContents(String content) {
        // TODO: 앱에서 null, 빈문자열, 띄어쓰기 다 막으면 hasText 로 변경해야함.
        if (Objects.isNull(content)) {
            throw new IllegalArgumentException("'content' must not be null");
        }
        this.contents = content;
    }

    private void setRating(Integer rating) {
        if (rating == null) {
            throw new IllegalArgumentException("'rating' must not be null");
        }
        if (rating < 0 || rating > RATING_MAX) {
            throw new IllegalArgumentException("'rating' must be greater than zero and less then or equal to " + RATING_MAX + ". rating: " + rating);
        }
        this.rating = rating;
    }

    private void setStatusDeleted() {
        this.status = ReviewStatus.DELETED;
    }
}
