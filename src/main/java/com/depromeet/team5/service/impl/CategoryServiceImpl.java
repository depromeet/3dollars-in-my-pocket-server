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
    public CategoryDto getDistanceList(Double latitude, Double longitude, CategoryTypes category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setStoreList50(DistanceList(latitude, longitude, 0D, 0.05D, category.toString()));
        categoryDto.setStoreList100(DistanceList(latitude, longitude, 0.05D, 0.1D, category.toString()));
        categoryDto.setStoreList500(DistanceList(latitude, longitude, 0.1D, 0.5D, category.toString()));
        categoryDto.setStoreList1000(DistanceList(latitude, longitude, 0.5D, 1D, category.toString()));
        return categoryDto;
    }

    @Override
    @Transactional
    public CategoryDto getReviewList(Double latitude, Double longitude, CategoryTypes category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setStoreList50(ReviewList(latitude, longitude, 0D, 0.05D, category.toString()));
        categoryDto.setStoreList100(ReviewList(latitude, longitude, 0.05D, 0.1D, category.toString()));
        categoryDto.setStoreList500(ReviewList(latitude, longitude, 0.1D, 0.5D, category.toString()));
        categoryDto.setStoreList1000(ReviewList(latitude, longitude, 0.5D, 1D, category.toString()));
        return categoryDto;
    }

    private List<StoreCardDto> DistanceList(Double latitude, Double longitude, Double radiusStart, Double radiusEnd, String category) {
        List<StoreCardDto> storeList = storeRepository.findAllByDistance(latitude, longitude, radiusStart, radiusEnd, category)
                .stream()
                .map(StoreCardDto::from)
                .collect(Collectors.toList());

        for (StoreCardDto storeCardDto : storeList) {
            StoreCardDto.calculationDistance(storeCardDto, latitude, longitude);
        }
        return storeList;
    }

    private List<StoreCardDto> ReviewList(Double latitude, Double longitude, Double radiusStart, Double radiusEnd, String category) {
        List<StoreCardDto> storeList = storeRepository.findAllByReview(latitude, longitude,  radiusStart, radiusEnd, category)
                .stream()
                .map(StoreCardDto::from)
                .collect(Collectors.toList());

        for (StoreCardDto storeCardDto : storeList) {
            StoreCardDto.calculationDistance(storeCardDto, latitude, longitude);
        }
        return storeList;
    }

}
