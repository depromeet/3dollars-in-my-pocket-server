package com.depromeet.team5.dto;

import com.depromeet.team5.domain.faq.Faq;
import com.depromeet.team5.domain.faq.FaqTagMap;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Data
public class FaqResponseDto {
    private Long id;
    private String question;
    private String answer;
    private Set<FaqTagResponseDto> tags;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static FaqResponseDto from(Faq faq) {
        FaqResponseDto faqResponseDto = new FaqResponseDto();
        faqResponseDto.setId(faq.getFaqId());
        faqResponseDto.setQuestion(faq.getQuestion());
        faqResponseDto.setAnswer(faq.getAnswer());
        faqResponseDto.setTags(faq.getFaqTagMaps()
                                  .stream()
                                  .map(FaqTagMap::getFaqTag)
                                  .map(FaqTagResponseDto::from)
                                  .collect(toSet()));
        faqResponseDto.setCreatedAt(faq.getCreatedAt());
        faqResponseDto.setUpdatedAt(faq.getUpdatedAt());
        return faqResponseDto;
    }
}
