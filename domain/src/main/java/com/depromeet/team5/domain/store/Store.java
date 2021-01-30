package com.depromeet.team5.domain.store;

import com.depromeet.team5.domain.review.Review;
import com.depromeet.team5.domain.user.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.stream.Collectors;

@Entity
@Getter
@EqualsAndHashCode(of = {"id", "latitude", "longitude", "storeName", "category", "createdAt"})
@EntityListeners(AuditingEntityListener.class)
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 위치 (위도)
     */
    private Double latitude;
    /**
     * 위치 (경도)
     */
    private Double longitude;
    /**
     * 이름
     */
    private String storeName;

    /**
     * 가게 종류
     */
    @Enumerated(value = EnumType.STRING)
    private StoreType storeType;

    /**
     * 가게 카테고리는 없어지고, 메뉴 카테고리를 사용합니다.
     * 가게마다 대표카테고리는 존재하는데, 메뉴 개수나 개인화 점수 등 데이터에 따라 동적으로 변경될 수 있어서 db 에 값을 저장하지 않습니다.
     */
    @Deprecated
    @Enumerated(value = EnumType.STRING)
    private CategoryType category;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AppearanceDay> appearanceDays = new HashSet<>();

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PaymentMethod> paymentMethods = new HashSet<>();

    /**
     * 가게 사진
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "image_id")
    private List<Image> image = new ArrayList<>(); // FIXME: storeId

    /**
     * 메뉴
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "menu_id") // FIXME: storeId
    private List<Menu> menu = new ArrayList<>();
    /**
     * 리뷰
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "storeId")
    private List<Review> review = new ArrayList<>();
    /**
     * 평균 평점
     */
    private Float rating;
    /**
     * 삭제 요청
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "delete_id") // FIXME: storeId
    private List<DeleteRequest> deleteRequest = new ArrayList<>();
    /**
     * 제보자
     */
    @ManyToOne
    private User user;
    /**
     * 메뉴에 포함된 카테고리 목록
     */
    @Deprecated
    @OneToMany(mappedBy = "store")
    private final List<StoreMenuCategory> storeMenuCategories = new ArrayList<>();
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

    /**
     * 새 가게를 생성합니다.
     * @param storeCreateValue 가게 생성 정보
     * @param user 제보자
     * @return 새로 생성한 가게
     */
    public static Store from(StoreCreateValue storeCreateValue, User user) {
        Store store = new Store();
        store.latitude = storeCreateValue.getLatitude();
        store.longitude = storeCreateValue.getLongitude();
        store.storeName = storeCreateValue.getStoreName();
        store.storeType = storeCreateValue.getStoreType();
        store.category = storeCreateValue.getCategory();
        store.user = user;

        if (storeCreateValue.getAppearanceDays() != null) {
            for (DayOfWeek day : storeCreateValue.getAppearanceDays()) {
                store.appearanceDays.add(AppearanceDay.from(store, day));
            }
        }
        if (storeCreateValue.getPaymentMethods() != null) {
            for (PaymentMethodType paymentMethodType : storeCreateValue.getPaymentMethods()) {
                store.paymentMethods.add(PaymentMethod.from(store, paymentMethodType));
            }
        }
        if (storeCreateValue.getMenus() != null) {
            store.menu = storeCreateValue.getMenus().stream().map(Menu::from).collect(Collectors.toList());
        }

        return store;
    }

    /**
     * 가게 정보 수정
     * @param storeUpdateValue 가게 수정할 정보
     */
    public void setStore(StoreUpdateValue storeUpdateValue) {
        // TODO: 가게 위치 수정 막으려고 주석 추가함. 가게 위치 수정 허용되면 주석 해제 필요.
        // latitude = storeUpdateValue.getLatitude();
        // longitude = storeUpdateValue.getLongitude();
        storeName = storeUpdateValue.getStoreName();
        storeType = storeUpdateValue.getStoreType();
        appearanceDays.clear();
        paymentMethods.clear();
        menu.clear();
        if (storeUpdateValue.getAppearanceDays() != null) {
            for (DayOfWeek day : storeUpdateValue.getAppearanceDays()) {
                appearanceDays.add(AppearanceDay.from(this, day));
            }
        }
        if (storeUpdateValue.getPaymentMethods() != null) {
            for (PaymentMethodType paymentMethodType : storeUpdateValue.getPaymentMethods()) {
                paymentMethods.add(PaymentMethod.from(this, paymentMethodType));
            }
        }
        if (storeUpdateValue.getMenus() != null) {
            menu.addAll(storeUpdateValue.getMenus().stream().map(Menu::from).collect(Collectors.toList()));
        }
    }

    /**
     * 가게 삭제 요청
     * @param storeId 삭제 요청한 가게
     * @param userId 삭제 요청자 식별자
     * @param deleteReasonType 삭제 정보
     */
    public void addDeleteId(Long storeId, Long userId, DeleteReasonType deleteReasonType) {
        DeleteRequest deleteRequestId = new DeleteRequest();
        deleteRequestId.setStoreId(storeId); // FIXME: 가게 정보는 storeId 를 따로 입력받지 않고, this.id 를 사용하면 됨.
        deleteRequestId.setUserId(userId);
        deleteRequestId.setReason(deleteReasonType);
        deleteRequest.add(deleteRequestId);
    }

    /**
     * 가게 카테고리 추가
     * @param storeMenuCategory 메뉴에 추가된 가게 카테고리
     */
    public void addStoreMenuCategory(StoreMenuCategory storeMenuCategory) {
        storeMenuCategories.add(storeMenuCategory);
    }

    /**
     * 평점 갱신
     * @param rating 리뷰가 추가/수정/삭제되면서 변경된 평점의 평균 값
     */
    public void setRating(Float rating) {
        this.rating = rating;
    }

    /**
     * 대표 카테고리 조회
     * @return 대표 카테고리
     */
    public CategoryType getCategory() {
        if (category != null) {
            return category;
        }
        return this.resolveRepresentativeCategory();
    }

    private CategoryType resolveRepresentativeCategory() {
        Map<CategoryType, List<Menu>> categoryMenuMap = menu.stream().collect(Collectors.groupingBy(Menu::getCategory));
        CategoryType categoryType = CategoryType.BUNGEOPPANG;
        int count = 0;
        for (Map.Entry<CategoryType, List<Menu>> entry : categoryMenuMap.entrySet()) {
            int size = entry.getValue().size();
            if (count < size) {
                categoryType = entry.getKey();
                count = size;
            }
        }
        return categoryType;
    }

    /**
     * 이미지 추가
     * @param imageList 이미지 목록
     */
    public void addImages(List<Image> imageList) {
        this.image.addAll(imageList);
    }
}
