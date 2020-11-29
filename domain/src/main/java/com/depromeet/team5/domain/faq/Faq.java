package com.depromeet.team5.domain.faq;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter(AccessLevel.PRIVATE)
public class Faq {
    @Id
    @GeneratedValue
    private Long faqId;

    private String question;

    private String answer;

    @OneToMany(mappedBy = "faq")
    private Set<FaqTagMap> faqTagMaps = new HashSet<>();

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public static Faq of(String question, String answer) {
        return new Faq(null, question, answer, new HashSet<>(), null, null);
    }

    public boolean addFaqTagMap(FaqTagMap faqTagMap) {
        return faqTagMaps.add(faqTagMap);
    }

    public boolean removeFaqTagMap(FaqTagMap faqTagMap) {
        return faqTagMaps.remove(faqTagMap);
    }

    public Faq update(FaqContentVo faqContentVo) {
        Optional.ofNullable(faqContentVo.getQuestion())
                .ifPresent(this::setQuestion);
        Optional.ofNullable(faqContentVo.getAnswer())
                .ifPresent(this::setAnswer);
        return this;
    }
}
