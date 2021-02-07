package com.depromeet.team5.domain.store;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class AppearanceDay {

    private AppearanceDay(Store store, DayOfWeek day) {
        this.store = store;
        this.day = day;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @Enumerated(value = EnumType.STRING)
    private DayOfWeek day;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public boolean equals(Object appearanceDay) {
        AppearanceDay ad = (AppearanceDay) appearanceDay;
        return Objects.equals(getDay(), ad.getDay());
    }

    public int hashCode() {
        return day.hashCode();
    }

    public static AppearanceDay from(Store store, DayOfWeek day) {
        return new AppearanceDay(store, day);
    }
}
