package com.depromeet.team5.service;

import com.depromeet.team5.domain.store.MenuCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MenuCategoryService {
    MenuCategory create(String name);

    Page<MenuCategory> getMenuCategories(Pageable pageable);
}
