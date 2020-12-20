package com.depromeet.team5.service;

import com.depromeet.team5.domain.store.CategoryTypes;
import com.depromeet.team5.domain.store.Store;
import com.depromeet.team5.domain.store.StoresByCategoryAndRating;

import java.util.List;

public interface CategoryService {
    List<Store> getStoreByCategoryAndDistanceBetween(Double latitude, Double longitude, Double distanceStart, Double distanceEnd, CategoryTypes categoryType);

    StoresByCategoryAndRating getStoresByCategoryAndRating(Double latitude, Double longitude, CategoryTypes category);
}
