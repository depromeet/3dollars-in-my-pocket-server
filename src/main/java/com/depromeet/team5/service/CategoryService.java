package com.depromeet.team5.service;

import com.depromeet.team5.domain.CategoryTypes;
import com.depromeet.team5.dto.StoreCardDto;

import java.util.List;

public interface CategoryService {
    List<StoreCardDto> getDistanceList(Float latitude, Float longitude,CategoryTypes category);
    List<StoreCardDto> getReviewList(Float latitude, Float longitude,CategoryTypes category);

}
