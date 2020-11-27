package com.depromeet.team5.domain;

import com.depromeet.team5.domain.user.User;
import com.depromeet.team5.dto.StoreDto;
import com.depromeet.team5.dto.StoreUpdateDto;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
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

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public static Store from(StoreDto storeDto, List<Image> imageList, User user) {
        Store store = new Store();
        store.latitude = storeDto.getLatitude();
        store.longitude = storeDto.getLongitude();
        store.storeName = storeDto.getStoreName();
        store.category = storeDto.getCategory();
        store.image = imageList;
        store.review = new ArrayList<>();
        store.rating = 0F;
        store.deleteRequest = new ArrayList<>();
        store.user = user;
        if (storeDto.getMenu() != null){
            store.menu = storeDto.getMenu().stream().map(Menu::from).collect(Collectors.toList());
        } else {
            store.menu = new ArrayList<>();
        }
        return store;
    }

    public void setStore(StoreUpdateDto storeUpdateDto, List<Image> imageList) {
        latitude = storeUpdateDto.getLatitude();
        longitude = storeUpdateDto.getLongitude();
        storeName = storeUpdateDto.getStoreName();
        image.addAll(imageList);
        menu.clear();
        if (storeUpdateDto.getMenu() != null){
            menu.addAll(storeUpdateDto.getMenu().stream().map(Menu::from).collect(Collectors.toList()));
        }
    }

    public void addDeleteId(Long storeId, Long userId, DeleteReasonType deleteReasonType) {
        DeleteRequest deleteRequestId = new DeleteRequest();
        deleteRequestId.setStoreId(storeId);
        deleteRequestId.setUserId(userId);
        deleteRequestId.setReason(deleteReasonType);
        deleteRequest.add(deleteRequestId);
    }
}
