package com.depromeet.team5.service.impl;

import com.depromeet.team5.domain.store.CategoryTypes;
import com.depromeet.team5.dto.CategoryDistanceDto;
import com.depromeet.team5.dto.CategoryReviewDto;
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
    public CategoryDistanceDto getDistanceList(Double latitude, Double longitude, CategoryTypes category) {
        CategoryDistanceDto categoryDto = new CategoryDistanceDto();
        categoryDto.setStoreList50(DistanceList(latitude, longitude, 0D, 0.05D, category.toString()));
        categoryDto.setStoreList100(DistanceList(latitude, longitude, 0.05D, 0.1D, category.toString()));
        categoryDto.setStoreList500(DistanceList(latitude, longitude, 0.1D, 0.5D, category.toString()));
        categoryDto.setStoreList1000(DistanceList(latitude, longitude, 0.5D, 1D, category.toString()));
        return categoryDto;
    }

    @Override
    @Transactional
    public CategoryReviewDto getReviewList(Double latitude, Double longitude, CategoryTypes category) {
        CategoryReviewDto categoryDto = new CategoryReviewDto();
        categoryDto.setStoreList4(ReviewList(latitude, longitude, category.toString(), 4F, 5.1F));
        categoryDto.setStoreList3(ReviewList(latitude, longitude, category.toString(), 3.0F, 4.0F));
        categoryDto.setStoreList2(ReviewList(latitude, longitude, category.toString(), 2.0F, 3.0F));
        categoryDto.setStoreList1(ReviewList(latitude, longitude, category.toString(), 1.0F, 2.0F));
        categoryDto.setStoreList0(ReviewList(latitude, longitude, category.toString(), 0.0F, 1.0F));
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

    private List<StoreCardDto> ReviewList(Double latitude, Double longitude, String category, Float ratingStart, Float ratingEnd) {
        List<StoreCardDto> storeList = storeRepository.findAllByReview(latitude, longitude, category, ratingStart, ratingEnd)
                .stream()
                .map(StoreCardDto::from)
                .collect(Collectors.toList());

        for (StoreCardDto storeCardDto : storeList) {
            StoreCardDto.calculationDistance(storeCardDto, latitude, longitude);
        }
        return storeList;
    }

}
