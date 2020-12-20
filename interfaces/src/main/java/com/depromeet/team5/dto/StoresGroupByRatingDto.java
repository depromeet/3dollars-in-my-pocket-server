package com.depromeet.team5.dto;

import lombok.Data;

import java.util.List;

@Data
public class StoresGroupByRatingDto {

    private List<StoreCardDto> storeList4;

    private List<StoreCardDto> storeList3;

    private List<StoreCardDto> storeList2;

    private List<StoreCardDto> storeList1;

    private List<StoreCardDto> storeList0;
}
