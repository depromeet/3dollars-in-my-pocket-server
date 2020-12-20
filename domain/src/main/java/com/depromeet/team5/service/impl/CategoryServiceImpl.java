package com.depromeet.team5.service.impl;

import com.depromeet.team5.domain.store.CategoryTypes;
import com.depromeet.team5.domain.store.Store;
import com.depromeet.team5.domain.store.StoresByCategoryAndDistance;
import com.depromeet.team5.domain.store.StoresByCategoryAndRating;
import com.depromeet.team5.repository.StoreRepository;
import com.depromeet.team5.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final StoreRepository storeRepository;

    @Override
    @Transactional(readOnly = true)
    public StoresByCategoryAndDistance getStoresByCategoryAndDistance(Double latitude, Double longitude, CategoryTypes category) {
        return StoresByCategoryAndDistance.of(
                getStoreByCategoryAndDistanceBetween(latitude, longitude, 0D, 0.05D, category),
                getStoreByCategoryAndDistanceBetween(latitude, longitude, 0.05D, 0.1D, category),
                getStoreByCategoryAndDistanceBetween(latitude, longitude, 0.1D, 0.5D, category),
                getStoreByCategoryAndDistanceBetween(latitude, longitude, 0.5D, 1D, category)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public StoresByCategoryAndRating getStoresByCategoryAndRating(Double latitude, Double longitude, CategoryTypes category) {
        return StoresByCategoryAndRating.of(
                getStoresByCategoryAndRatingBetween(latitude, longitude, category, 4F, 5.1F),
                getStoresByCategoryAndRatingBetween(latitude, longitude, category, 3.0F, 4.0F),
                getStoresByCategoryAndRatingBetween(latitude, longitude, category, 2.0F, 3.0F),
                getStoresByCategoryAndRatingBetween(latitude, longitude, category, 1.0F, 2.0F),
                getStoresByCategoryAndRatingBetween(latitude, longitude, category, 0.0F, 1.0F)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<Store> getStoreByCategoryAndDistanceBetween(Double latitude, Double longitude, Double distanceStart, Double distanceEnd, CategoryTypes categoryType) {
        return storeRepository.findAllByDistance(latitude, longitude, distanceStart, distanceEnd, categoryType.name());
    }

    private List<Store> getStoresByCategoryAndRatingBetween(Double latitude, Double longitude, CategoryTypes categoryType, Float ratingStart, Float ratingEnd) {
        return storeRepository.findAllByReview(latitude, longitude, categoryType.name(), ratingStart, ratingEnd);
    }
}
