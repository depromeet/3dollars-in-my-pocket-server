package com.depromeet.team5.domain;

import com.depromeet.team5.dto.MenuDto;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long price;

    public static Menu from(MenuDto menuDto) {
        Menu menu = new Menu();
        menu.setName(menuDto.getName());
        menu.setPrice(menuDto.getPrice());
        return menu;
    }
}
