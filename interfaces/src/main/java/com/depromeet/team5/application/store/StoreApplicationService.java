package com.depromeet.team5.application.store;

import com.depromeet.team5.domain.store.Store;
import com.depromeet.team5.dto.StoreDetailDto;
import com.depromeet.team5.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StoreApplicationService {
    private final StoreService storeService;
    private final StoreAssembler storeAssembler;

    public StoreDetailDto getStoreDetail(Long storeId, Double latitude, Double longitude) {
        Store store = storeService.getStore(storeId);
        return storeAssembler.toStoreDetailDto(store, latitude, longitude);
    }
}
