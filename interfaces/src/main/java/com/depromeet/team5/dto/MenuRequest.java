package com.depromeet.team5.dto;

import com.depromeet.team5.domain.store.CategoryType;
import lombok.Data;

@Data
public class MenuRequest {
    /**
     * 이름
     */
    private String name;
    /**
     * 가격
     */
    private String price;
    /**
     * 카테고리 (붕어빵, 문어빵, 계란빵, 호떡, ...)
     */
    private CategoryType category;
}
