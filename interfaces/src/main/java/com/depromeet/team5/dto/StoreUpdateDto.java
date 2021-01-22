package com.depromeet.team5.dto;

import com.depromeet.team5.domain.store.AppearanceDayType;
import com.depromeet.team5.domain.store.PaymentMethodType;
import com.depromeet.team5.domain.store.StoreType;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class StoreUpdateDto {

    private Double latitude;

    private Double longitude;

    private String storeName;

    private StoreType storeType;

    private List<AppearanceDayType> appearanceDays;

    private List<PaymentMethodType> paymentMethods;

    private List<MultipartFile> image;

    private List<MenuDto> menu;
}
