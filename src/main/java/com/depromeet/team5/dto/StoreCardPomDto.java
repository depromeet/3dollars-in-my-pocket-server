package com.depromeet.team5.dto;

import java.util.List;

public class StoreCardPomDto {

    private List<StoreCardDto> content;

    private Long totalElements;

    private int totalPages;

    public static StoreCardPomDto from(List<StoreCardDto> content, Long totalElements, int totalPages) {
        StoreCardPomDto StoreCardPomDto = new StoreCardPomDto();
        StoreCardPomDto.content = content;
        StoreCardPomDto.totalElements = totalElements;
        StoreCardPomDto.totalPages = totalPages;
        return StoreCardPomDto;
    }
}
