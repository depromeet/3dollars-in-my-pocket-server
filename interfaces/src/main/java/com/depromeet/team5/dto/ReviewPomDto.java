package com.depromeet.team5.dto;

import com.depromeet.team5.domain.store.Review;
import lombok.Data;

import java.util.List;

@Data
public class ReviewPomDto {

    private List<Review> content;

    private Long totalElements;

    private int totalPages;

    public static ReviewPomDto from(List<Review> content, Long totalElements, int totalPages) {
        ReviewPomDto ReviewPomDto = new ReviewPomDto();
        ReviewPomDto.content = content;
        ReviewPomDto.totalElements = totalElements;
        ReviewPomDto.totalPages = totalPages;
        return ReviewPomDto;
    }
}
