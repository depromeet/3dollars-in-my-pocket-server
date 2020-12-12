package com.depromeet.team5.exception;

import com.depromeet.team5.domain.ResultCode;

import java.util.Optional;

public class StoreNotFoundException extends NotFoundException {
    public StoreNotFoundException(Long storeId){
        super("가게를 찾을 수 없습니다. storeId:" + storeId);
    }

    @Override
    public Optional<ResultCode> getResultCode() {
        return Optional.of(ResultCode.STORE_NOT_FOUND);
    }
}