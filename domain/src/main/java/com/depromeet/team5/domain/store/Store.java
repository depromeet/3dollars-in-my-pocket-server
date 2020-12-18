package com.depromeet.team5.domain.store;

import com.depromeet.team5.domain.review.Review;
import com.depromeet.team5.domain.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@EqualsAndHashCode(of = {"id", "latitude", "longitude", "storeName", "category", "createdAt"})
@EntityListeners(AuditingEntityListener.class)
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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "storeId")
    private List<Review> review = new ArrayList<>();

    private Float rating;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "delete_id")
    private List<DeleteRequest> deleteRequest = new ArrayList<>();

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "store")
    private final List<StoreMenuCategory> storeMenuCategories = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public static Store from(StoreCreateValue storeCreateValue, List<Image> imageList, User user) {
        Store store = new Store();
        store.latitude = storeCreateValue.getLatitude();
        store.longitude = storeCreateValue.getLongitude();
        store.storeName = storeCreateValue.getStoreName();
        store.category = storeCreateValue.getCategory();
        store.image = imageList;
        store.review = new ArrayList<>();
        store.rating = 0F;
        store.deleteRequest = new ArrayList<>();
        store.user = user;
        if (storeCreateValue.getMenus() != null){
            store.menu = storeCreateValue.getMenus().stream().map(Menu::from).collect(Collectors.toList());
        } else {
            store.menu = new ArrayList<>();
        }
        return store;
    }

    public void setStore(StoreUpdateValue storeUpdateValue, List<Image> imageList) {
        // latitude = storeUpdateValue.getLatitude();
        // longitude = storeUpdateValue.getLongitude();
        storeName = storeUpdateValue.getStoreName();
        image.addAll(imageList);
        menu.clear();
        if (storeUpdateValue.getMenus() != null){
            menu.addAll(storeUpdateValue.getMenus().stream().map(Menu::from).collect(Collectors.toList()));
        }
    }

    public void addDeleteId(Long storeId, Long userId, DeleteReasonType deleteReasonType) {
        DeleteRequest deleteRequestId = new DeleteRequest();
        deleteRequestId.setStoreId(storeId);
        deleteRequestId.setUserId(userId);
        deleteRequestId.setReason(deleteReasonType);
        deleteRequest.add(deleteRequestId);
    }

    public boolean addStoreMenuCategory(StoreMenuCategory storeMenuCategory) {
        return storeMenuCategories.add(storeMenuCategory);
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }
}
