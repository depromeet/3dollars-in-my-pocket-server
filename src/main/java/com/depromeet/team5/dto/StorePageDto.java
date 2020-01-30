package com.depromeet.team5.dto;

import java.util.List;

public class StorePageDto {

    private List<StoreCardDto> content;

    private Long totalElements;

    private int totalPages;

    public static StorePageDto from(List<StoreCardDto> content, Long totalElements, int totalPages) {
        StorePageDto StorePageDto = new StorePageDto();
        StorePageDto.content = content;
        StorePageDto.totalElements = totalElements;
        StorePageDto.totalPages = totalPages;
        return StorePageDto;
    }
}
