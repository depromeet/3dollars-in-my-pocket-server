package com.depromeet.team5.service.impl;

import com.depromeet.team5.domain.CategoryTypes;
import com.depromeet.team5.dto.CategoryDto;
import com.depromeet.team5.dto.StoreCardDto;
import com.depromeet.team5.repository.StoreRepository;
import com.depromeet.team5.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final StoreRepository storeRepository;

    @Override
    @Transactional
    public CategoryDto getDistanceList(Float latitude, Float longitude, CategoryTypes category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setStoreList50(DistanceList(latitude, longitude, 0F, 50F, category));
        categoryDto.setStoreList100(DistanceList(latitude, longitude, 50F, 100F, category));
        return categoryDto;
    }

    @Override
    @Transactional
    public CategoryDto getReviewList(Float latitude, Float longitude, CategoryTypes category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setStoreList50(ReviewList(latitude, longitude, 0F, 50F, category));
        categoryDto.setStoreList100(ReviewList(latitude, longitude, 50F, 100F, category));
        return categoryDto;
    }



    private List<StoreCardDto> DistanceList(Float latitude, Float longitude, Float radiusStart, Float radiusEnd, CategoryTypes category) {
        List<StoreCardDto> storeList = storeRepository.findAllByDistance(latitude, longitude, radiusStart, radiusEnd)
                .stream()
                .map(StoreCardDto::from)
                .collect(Collectors.toList());

        for (StoreCardDto storeCardDto : storeList) {
            StoreCardDto.calculationDistance(storeCardDto, latitude, longitude);
        }
        return storeList;
    }

    private List<StoreCardDto> ReviewList(Float latitude, Float longitude, Float radiusStart, Float radiusEnd, CategoryTypes category) {
        List<StoreCardDto> storeList = storeRepository.findAllByReview(latitude, longitude,  radiusStart, radiusEnd)
                .stream()
                .map(StoreCardDto::from)
                .collect(Collectors.toList());

        for (StoreCardDto storeCardDto : storeList) {
            StoreCardDto.calculationDistance(storeCardDto, latitude, longitude);
        }
        return storeList;
    }

}
