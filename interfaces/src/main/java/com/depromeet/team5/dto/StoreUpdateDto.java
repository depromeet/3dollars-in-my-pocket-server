package com.depromeet.team5.dto;

import com.depromeet.team5.domain.store.PaymentMethodType;
import com.depromeet.team5.domain.store.StoreType;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Data
public class StoreUpdateDto {

    private Double latitude;

    private Double longitude;

    private String storeName;

    private StoreType storeType;

    private Set<DayOfWeek> appearanceDays;

    private Set<PaymentMethodType> paymentMethods;

    private List<MultipartFile> image;

    private List<MenuDto> menu;
}
