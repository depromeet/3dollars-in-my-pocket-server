package com.depromeet.team5.service;

import com.depromeet.team5.domain.Store;
import com.depromeet.team5.dto.StoreCardDto;
import com.depromeet.team5.dto.StoreDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StoreService {
    void saveStore(StoreDto storeDto, Long userId);
    List<StoreCardDto> getAll(Float latitude, Float longitude);
    Store getDetail(Long storeId);
    void updateStore(StoreDto storeDto, Long storeId);
    void deleteStore(Long storeId);
}
