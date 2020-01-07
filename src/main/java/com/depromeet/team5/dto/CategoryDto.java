package com.depromeet.team5.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoryDto {
    private List<StoreCardDto> storeList50;
    private List<StoreCardDto> storeList100;
}
