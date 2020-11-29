package com.depromeet.team5.service;

import com.depromeet.team5.domain.store.CategoryTypes;
import com.depromeet.team5.domain.store.StoresByCategoryAndDistance;
import com.depromeet.team5.domain.store.StoresByCategoryAndRating;

public interface CategoryService {
    StoresByCategoryAndDistance getStoresByCategoryAndDistance(Double latitude, Double longitude, CategoryTypes category);
    StoresByCategoryAndRating getStoresByCategoryAndRating(Double latitude, Double longitude, CategoryTypes category);
}
