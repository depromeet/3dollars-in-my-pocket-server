package com.depromeet.team5.domain.store;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode(of = {"storeId", "menuCategoryId"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StoreMenuCategoryId implements Serializable {

    private static final long serialVersionUID = 8513789472354465800L;
    @Column
    private Long storeId;
    @Column
    private Long menuCategoryId;

    public static StoreMenuCategoryId of(Store store, MenuCategory menuCategory) {
        return new StoreMenuCategoryId(store.getId(), menuCategory.getId());
    }
}
