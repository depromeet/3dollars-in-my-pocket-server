package com.depromeet.team5.domain.faq;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FaqTag {
    @Id
    @GeneratedValue
    private Long faqTagId;
    private String name;

    public static FaqTag from(String name) {
        FaqTag faqTag = new FaqTag();
        faqTag.name = name;
        return faqTag;
    }
}
