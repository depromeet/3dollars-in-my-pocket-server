package com.depromeet.team5.domain.faq;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FaqTagMap {
    @EmbeddedId
    private FaqTagMapId faqTagMapId;

    @ManyToOne
    @MapsId("faqId")
    @JoinColumn(name = "faq_id")
    private Faq faq;

    @ManyToOne
    @MapsId("faqTagId")
    @JoinColumn(name = "faq_tag_id")
    private FaqTag faqTag;

    public static FaqTagMap of(Faq faq, FaqTag faqTag) {
        FaqTagMap faqTagMap = new FaqTagMap();
        faqTagMap.faqTagMapId = FaqTagMapId.of(faq, faqTag);
        faqTagMap.faq = faq;
        faqTagMap.faqTag = faqTag;
        return faqTagMap;
    }
}
