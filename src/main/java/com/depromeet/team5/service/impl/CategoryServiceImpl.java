package com.depromeet.team5.service.impl;

import com.depromeet.team5.domain.CategoryTypes;
import com.depromeet.team5.domain.Store;
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
    public List<StoreCardDto> getDistanceList(Float latitude, Float longitude, Float radius, CategoryTypes category) {
        List<StoreCardDto> storeList = storeRepository.findAllByDistance(latitude, longitude, radius, category)
                .stream()
                .map(StoreCardDto::from)
                .collect(Collectors.toList());

        for (StoreCardDto storeCardDto : storeList) {
            StoreCardDto.calculationDistance(storeCardDto, latitude, longitude);
        }
        return storeList;
    }
}
