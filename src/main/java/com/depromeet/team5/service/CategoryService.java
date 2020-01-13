package com.depromeet.team5.service;

import com.depromeet.team5.domain.CategoryTypes;
import com.depromeet.team5.dto.CategoryDto;

public interface CategoryService {
    CategoryDto getDistanceList(Double latitude, Double longitude, CategoryTypes category);
    CategoryDto getReviewList(Double latitude, Double longitude,CategoryTypes category);
}
