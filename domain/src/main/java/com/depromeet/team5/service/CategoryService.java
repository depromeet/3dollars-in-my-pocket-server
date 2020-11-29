package com.depromeet.team5.service;

import com.depromeet.team5.domain.store.CategoryTypes;
import com.depromeet.team5.dto.CategoryDistanceDto;
import com.depromeet.team5.dto.CategoryReviewDto;

public interface CategoryService {
    CategoryDistanceDto getDistanceList(Double latitude, Double longitude, CategoryTypes category);
    CategoryReviewDto getReviewList(Double latitude, Double longitude, CategoryTypes category);
}
