package com.depromeet.team5.dto;

import com.depromeet.team5.domain.store.Menu;
import lombok.Data;

@Data
public class MenuResponse {
    /**
     * 메뉴 식별자
     */
    private Long id;
    /**
     * 메뉴 카테고리
     */
    private String category;
    /**
     * 메뉴 이름
     */
    private String name;
    /**
     * 메뉴 가격
     */
    private String price;

    public static MenuResponse from(Menu menu) {
        if (menu == null) {
            return null;
        }
        MenuResponse menuResponse = new MenuResponse();
        menuResponse.setId(menu.getId());
        menuResponse.setCategory(menu.getCategory().name());
        menuResponse.setName(menu.getName());
        menuResponse.setPrice(menu.getPrice());
        return menuResponse;
    }
}
