package com.depromeet.team5.domain.store;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter(AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode(of = {"id", "category"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreCategory {
    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private CategoryType category;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public static StoreCategory from(CategoryType categoryType) {
        StoreCategory storeCategory = new StoreCategory();
        storeCategory.setCategory(categoryType);
        return storeCategory;
    }
}
