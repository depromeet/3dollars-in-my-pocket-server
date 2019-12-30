package com.depromeet.team5.domain;

import com.depromeet.team5.dto.MenuDto;
import com.depromeet.team5.dto.StoreDto;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.List;

public class Store {
    @Id
    @GeneratedValue
    private Long id;

    private Long latitude;

    private Long longitude;

    private String storeName;

    @Enumerated(value = EnumType.STRING)
    private CategoryTypes category;

    private List<MultipartFile> image;

    private List<MenuDto> menu;

    private Long review;

    @ManyToOne
    private User user;

    public static Store from(StoreDto storeDto, User user) {
        Store store = new Store();
        store.latitude = storeDto.getLatitude();
        store.longitude = storeDto.getLongitude();
        store.storeName = storeDto.getStoreName();
        store.category = storeDto.getCategory();
        store.image = storeDto.getImage();
        store.menu = storeDto.getMenu();
        store.review = 0L;
        store.user = user;
        return store;
    }


}
