package com.depromeet.team5.domain.user;

import com.depromeet.team5.domain.SocialTypes;
import com.depromeet.team5.repository.UserRepository;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private SocialTypes socialType;

    private String socialId;

    private String name;

    private Boolean state;

    @Enumerated(value = EnumType.STRING)
    private UserStatusType status;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public static User of(String socialId, SocialTypes socialType) {
        User user = new User();
        user.socialType = socialType;
        user.socialId = socialId;
        user.state = false;
        user.status = UserStatusType.ACTIVE;
        return user;
    }

    public void setName(String nickName) {
        name = nickName;
        state = true;
    }

    public String getName() {
        if (status == UserStatusType.INACTIVE)
            return "사라진 제보자";
        else
            return name;
    }

    public User resignin(WithdrawalUser withdrawalUser) {
        setName(withdrawalUser.getName());
        setStatus(UserStatusType.ACTIVE);
        setState(false);
        return this;
    }

    public User signout(UserRepository userRepository) {
        boolean changed = false;
        while (!changed) {
            String changedName = this.getName() + " " + (int) (Math.random() * 100);
            if (!userRepository.findByNameLike(changedName).isPresent()) {
                this.setName(changedName);
                this.setState(false);
                this.setStatus(UserStatusType.INACTIVE);
                changed = true;
            }
        }
        return this;
    }

    public boolean isWithdrawal() {
        return status != UserStatusType.ACTIVE;
    }
}
