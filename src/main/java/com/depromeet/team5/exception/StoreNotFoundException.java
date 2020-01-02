package com.depromeet.team5.exception;

public class StoreNotFoundException extends RuntimeException {
    public StoreNotFoundException(){
        super("식물을 찾을 수 없습니다.");
    }
}