package com.depromeet.team5.dto;

import java.util.List;

public class ReviewPomDto {

    private List<ReviewDto> content;

    private Long totalElements;

    private int totalPages;

    public static ReviewPomDto from(List<ReviewDto> content, Long totalElements, int totalPages) {
        ReviewPomDto ReviewPomDto = new ReviewPomDto();
        ReviewPomDto.content = content;
        ReviewPomDto.totalElements = totalElements;
        ReviewPomDto.totalPages = totalPages;
        return ReviewPomDto;
    }
}
