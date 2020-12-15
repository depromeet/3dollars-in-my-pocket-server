package com.depromeet.team5.repository;

import com.depromeet.team5.domain.store.StoreMenuCategory;
import com.depromeet.team5.domain.store.StoreMenuCategoryId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreMenuCategoryRepository extends JpaRepository<StoreMenuCategory, StoreMenuCategoryId> {
}
