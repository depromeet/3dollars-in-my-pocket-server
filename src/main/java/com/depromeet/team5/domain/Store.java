package com.depromeet.team5.domain;

import com.depromeet.team5.dto.StoreDto;
import com.depromeet.team5.dto.UpdateDto;
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

    private Float latitude;

    private Float longitude;

    private String storeName;

    @Enumerated(value = EnumType.STRING)
    private CategoryTypes category;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "image_id")
    private List<Image> image;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "menu_id")
    private List<Menu> menu = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "review_id")
    private List<Review> review = new ArrayList<>();

    @ManyToOne
    private User user;

    public static Store from(StoreDto storeDto, List<Image> imageList, User user) {
        Store store = new Store();
        store.latitude = storeDto.getLatitude();
        store.longitude = storeDto.getLongitude();
        store.storeName = storeDto.getStoreName();
        store.category = storeDto.getCategory();
        store.image = imageList;
        store.menu = storeDto.getMenu();
        store.user = user;
        return store;
    }

    public void setStore(UpdateDto updateDto, List<Image> imageList) {
        latitude = updateDto.getLatitude();
        longitude = updateDto.getLongitude();
        storeName = updateDto.getStoreName();
        image.addAll(imageList);
        menu.clear();
        menu.addAll(updateDto.getMenu());
    }

}
