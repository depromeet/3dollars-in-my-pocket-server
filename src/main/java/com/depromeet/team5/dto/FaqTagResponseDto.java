package com.depromeet.team5.dto;

import com.depromeet.team5.domain.faq.FaqTag;
import lombok.Data;

@Data
public class FaqTagResponseDto {
    private Long id;
    private String name;
    private Long displayOrder;

    public static FaqTagResponseDto from(FaqTag faqTag) {
        FaqTagResponseDto faqTagResponseDto = new FaqTagResponseDto();
        faqTagResponseDto.setId(faqTag.getFaqTagId());
        faqTagResponseDto.setName(faqTag.getName());
        faqTagResponseDto.setDisplayOrder(faqTag.getDisplayOrder());
        return faqTagResponseDto;
    }
}
