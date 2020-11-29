package com.depromeet.team5.exception;

import com.depromeet.team5.domain.ResultCode;

import java.text.MessageFormat;
import java.util.Optional;

public class StoreDeleteRequestDuplicatedException extends BadRequestException {
    public StoreDeleteRequestDuplicatedException(Long userId, Long storeId) {
        super(MessageFormat.format("이미 삭제 요청을 한 사용자입니다. userId: {0}, storeId: {1}",
                userId,
                storeId
        ));
    }

    @Override
    public Optional<ResultCode> getResultCode() {
        return Optional.of(ResultCode.STORE_DELETE_REQUEST_DUPLICATED);
    }
}
