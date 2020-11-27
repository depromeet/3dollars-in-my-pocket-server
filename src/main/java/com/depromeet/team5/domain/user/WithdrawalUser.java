package com.depromeet.team5.domain.user;

import com.depromeet.team5.domain.SocialTypes;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter(AccessLevel.PRIVATE)
public class WithdrawalUser {
    @Id
    private Long userId;

    private String name;

    @Enumerated(value = EnumType.STRING)
    private SocialTypes socialType;

    private String socialId;

    private LocalDateTime userCreatedAt;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public static WithdrawalUser from(User user) {
        return new WithdrawalUser(
                user.getId(),
                user.getName(),
                user.getSocialType(),
                user.getSocialId(),
                user.getCreatedAt(),
                null,
                null
        );
    }

    public WithdrawalUser update(User user) {
        this.setUserId(user.getId());
        this.setName(user.getName());
        this.setSocialType(user.getSocialType());
        this.setSocialId(user.getSocialId());
        this.setUserCreatedAt(user.getCreatedAt());
        return this;
    }
}
