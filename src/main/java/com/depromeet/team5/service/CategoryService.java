package com.depromeet.team5.service;

import com.depromeet.team5.domain.CategoryTypes;
import com.depromeet.team5.dto.CategoryDto;

public interface CategoryService {
    CategoryDto getDistanceList(Float latitude, Float longitude, CategoryTypes category);
    CategoryDto getReviewList(Float latitude, Float longitude,CategoryTypes category);
}
