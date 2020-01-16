package com.depromeet.team5.dto;

import com.depromeet.team5.domain.Menu;
import lombok.Data;

@Data
public class MenuDto {

    private String name;

    private String price;

    public static MenuDto from(Menu menu) {
        MenuDto menuDto = new MenuDto();
        menuDto.setName(menu.getName());
        menuDto.setPrice(menu.getPrice());
        return menuDto;
    }
}
