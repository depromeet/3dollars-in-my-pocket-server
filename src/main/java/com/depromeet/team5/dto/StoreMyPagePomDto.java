package com.depromeet.team5.dto;

import java.util.List;

public class StoreMyPagePomDto {
    private List<StoreMyPageDto> content;

    private Long totalElements;

    private int totalPages;

    public static StoreMyPagePomDto from(List<StoreMyPageDto> content, Long totalElements, int totalPages) {
        StoreMyPagePomDto StoreMyPagePomDto = new StoreMyPagePomDto();
        StoreMyPagePomDto.content = content;
        StoreMyPagePomDto.totalElements = totalElements;
        StoreMyPagePomDto.totalPages = totalPages;
        return StoreMyPagePomDto;
    }
}
