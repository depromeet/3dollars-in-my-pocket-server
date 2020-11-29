package com.depromeet.team5.exception;

public class ReviewNotFoundException extends RuntimeException{
    public ReviewNotFoundException() { super("해당하는 리뷰를 찾을 수 없습니다."); }
}
