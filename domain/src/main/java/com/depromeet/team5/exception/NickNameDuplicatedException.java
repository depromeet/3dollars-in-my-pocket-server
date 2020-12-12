package com.depromeet.team5.exception;

import com.depromeet.team5.domain.ResultCode;

import java.text.MessageFormat;
import java.util.Optional;

public class NickNameDuplicatedException extends BadRequestException {
    public NickNameDuplicatedException(Long userId, String name) {
        super(MessageFormat.format("같은 닉네임이 존재합니다. userId: {0}, name: {1}", userId, name));
    }

    @Override
    public Optional<ResultCode> getResultCode() {
        return Optional.of(ResultCode.USER_NICKNAME_DUPLICATED);
    }
}
