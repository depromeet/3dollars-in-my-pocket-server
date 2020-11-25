package com.depromeet.team5.domain.faq;

import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
public class FaqTagMapId implements Serializable {
    @Column
    private Long faqId;

    @Column
    private Long faqTagId;

    public static FaqTagMapId of(Faq faq, FaqTag faqTag) {
        FaqTagMapId faqTagMapId = new FaqTagMapId();
        faqTagMapId.faqId = faq.getFaqId();
        faqTagMapId.faqTagId = faqTag.getFaqTagId();
        return faqTagMapId;
    }
}
