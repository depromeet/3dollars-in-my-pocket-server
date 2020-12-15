package com.depromeet.team5.repository;

import com.depromeet.team5.domain.store.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuCategoryRepository extends JpaRepository<MenuCategory, Long> {
    Optional<MenuCategory> findByEnumName(String enumName);
}
