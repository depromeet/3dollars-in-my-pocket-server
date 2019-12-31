package com.depromeet.team5.domain;

import com.depromeet.team5.dto.StoreDto;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "store")
public class Store {
    @Id
    @GeneratedValue
    private Long id;

    private Long latitude;

    private Long longitude;

    private String storeName;

    @Enumerated(value = EnumType.STRING)
    private CategoryTypes category;

//    @ElementCollection
//    private List<String> image;
//
//    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
//    private List<Menu> menu = new ArrayList<>();

    private Long review;

    @ManyToOne
    private User user;

    public static Store from(StoreDto storeDto, List<String> image, User user) {
        Store store = new Store();
        store.latitude = storeDto.getLatitude();
        store.longitude = storeDto.getLongitude();
        store.storeName = storeDto.getStoreName();
        store.category = storeDto.getCategory();
//        store.image = image;
//        store.menu = storeDto.getMenu();
        store.review = 0L;
        store.user = user;
        return store;
    }


}
