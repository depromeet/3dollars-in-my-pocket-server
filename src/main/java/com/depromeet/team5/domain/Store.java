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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double latitude;

    private Double longitude;

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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "delete_id")
    private List<DeleteRequest> deleteRequest = new ArrayList<>();

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
        store.review = 0L;
        store.deleteRequest = new ArrayList<>();
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

    public void addDeleteId(Long userId) {
        DeleteRequest deleteRequestId = new DeleteRequest();
        deleteRequestId.setUserId(userId);
        deleteRequest.add(deleteRequestId);
    }

}
