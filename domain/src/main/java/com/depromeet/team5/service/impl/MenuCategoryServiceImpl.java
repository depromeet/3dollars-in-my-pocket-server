package com.depromeet.team5.service.impl;

import com.depromeet.team5.domain.store.MenuCategory;
import com.depromeet.team5.exception.MenuCategoryNameDuplicatedException;
import com.depromeet.team5.repository.MenuCategoryRepository;
import com.depromeet.team5.service.MenuCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MenuCategoryServiceImpl implements MenuCategoryService {
    private final MenuCategoryRepository menuCategoryRepository;

    @Override
    @Transactional
    public MenuCategory create(String name) {
        if (menuCategoryRepository.findByEnumName(name).isPresent()) {
            throw new MenuCategoryNameDuplicatedException();
        }
        return menuCategoryRepository.save(MenuCategory.from(name));
    }

    @Override
    public Page<MenuCategory> getMenuCategories(Pageable pageable) {
        return menuCategoryRepository.findAll(pageable);
    }
}
