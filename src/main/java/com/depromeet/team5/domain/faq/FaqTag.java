package com.depromeet.team5.domain.faq;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter(AccessLevel.PRIVATE)
public class FaqTag {
    @Id
    @GeneratedValue
    private Long faqTagId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Long displayOrder;

    public static FaqTag from(String name, Long displayOrder) {
        FaqTag faqTag = new FaqTag();
        faqTag.setName(name);
        faqTag.setDisplayOrder(displayOrder);
        return faqTag;
    }
}
