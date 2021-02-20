package com.depromeet.team5.domain.store;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 메뉴에 카테고리 추가하게 되어서, 가게에서는 카테고리 정보를 관리하지 않습니다.
 */
@Deprecated
@Table(
        uniqueConstraints = @UniqueConstraint(
                name = "unique_storeId_menuCategoryId",
                columnNames = {"store_id", "menu_category_id"}
        )
)
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = {"storeMenuCategoryId", "createdAt"})
@EntityListeners(AuditingEntityListener.class)
public class StoreMenuCategory {
    @EmbeddedId
    private StoreMenuCategoryId storeMenuCategoryId;
    @ManyToOne
    @MapsId("storeId")
    @JoinColumn(name = "store_id")
    private Store store;
    @ManyToOne
    @MapsId("menuCategoryId")
    @JoinColumn(name = "menu_category_id")
    private MenuCategory menuCategory;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public static StoreMenuCategory of(Store store, MenuCategory menuCategory) {
        return new StoreMenuCategory(
                StoreMenuCategoryId.of(store, menuCategory),
                store,
                menuCategory,
                null,
                null
        );
    }
}
