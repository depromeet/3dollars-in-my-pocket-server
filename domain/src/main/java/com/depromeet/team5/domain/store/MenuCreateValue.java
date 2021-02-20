package com.depromeet.team5.domain.store;

import lombok.Value;

@Value(staticConstructor = "of")
public class MenuCreateValue {
    /**
     * 메뉴 카테고리
     */
    CategoryType category;
    /**
     * 메뉴 이름
     */
    String name;
    /**
     * 메뉴 가격
     */
    String price;
}
