package com.depromeet.team5.exception;

import com.depromeet.team5.domain.ResultCode;

import java.util.Optional;

public class ImageNotFoundException extends NotFoundException {
    public ImageNotFoundException(Long imageId){
        super("이미지를 찾을 수 없습니다. imageId:" + imageId);
    }

    @Override
    public Optional<ResultCode> getResultCode() {
        return Optional.of(ResultCode.NOT_FOUND);
    }
}
