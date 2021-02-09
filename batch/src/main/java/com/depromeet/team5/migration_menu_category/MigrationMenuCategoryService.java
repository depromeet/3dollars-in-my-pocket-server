package com.depromeet.team5.migration_menu_category;

import com.depromeet.team5.domain.store.CategoryType;
import com.depromeet.team5.domain.store.Store;
import com.depromeet.team5.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MigrationMenuCategoryService {
    private final StoreRepository storeRepository;

    @Transactional
    public Slice<Store> migrate(Long storeId, Pageable pageable) {
        Slice<Store> storeSlice = storeRepository.findByIdGreaterThan(storeId, pageable);
        storeSlice.getContent().forEach(store -> {
            CategoryType categoryType = store.getCategory();
            store.getMenus().forEach(menu -> {
                if (menu.getCategory() == null) {
                    menu.setCategory(categoryType);
                }
            });
        });
        return storeSlice;
    }
}
