package com.depromeet.team5.domain;

import com.depromeet.team5.dto.StoreDto;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Store {
    @Id
    @GeneratedValue
    private Long id;

    private Long latitude;

    private Long longitude;

    private String storeName;

    @Enumerated(value = EnumType.STRING)
    private CategoryTypes category;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "image_id")
    private List<Image> image;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "menu_id")
    private List<Menu> menu = new ArrayList<>();

    private Long review;

    @ManyToOne
    private User user;

    public static Store from(StoreDto storeDto, List<Image> image, User user) {
        Store store = new Store();
        store.latitude = storeDto.getLatitude();
        store.longitude = storeDto.getLongitude();
        store.storeName = storeDto.getStoreName();
        store.category = storeDto.getCategory();
        store.image = image;
        store.menu = storeDto.getMenu();
        store.review = 0L;
        store.user = user;
        return store;
    }


}