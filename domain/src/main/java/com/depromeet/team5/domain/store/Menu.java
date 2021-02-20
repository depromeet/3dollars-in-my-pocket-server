package com.depromeet.team5.domain.store;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 메뉴 이름
     */
    private String name;
    /**
     * 메뉴 가격
     */
    private String price;
    /**
     * 메뉴 종류
     */
    @Enumerated(EnumType.STRING)
    private CategoryType category;
    /**
     * 생성 시각
     */
    @CreatedDate
    private LocalDateTime createdAt;
    /**
     * 수정 시각
     */
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public static Menu from(MenuCreateValue menuCreateValue) {
        Menu menu = new Menu();
        menu.setCategory(menuCreateValue.getCategory());
        menu.setName(menuCreateValue.getName());
        menu.setPrice(menuCreateValue.getPrice());
        return menu;
    }
}
