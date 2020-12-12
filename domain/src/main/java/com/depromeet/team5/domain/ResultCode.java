package com.depromeet.team5.domain;

public enum ResultCode {
    SUCCESS("정상적으로 처리되었습니다. ", "성공"),
    BAD_REQUEST("요청에 오류가 있습니다. 다시 확인해주세요. ", "클라이언트 에러"),
    NOT_FOUND("요청한 자원이 존재하지 않습니다. 다시 확인해주세요. ", "리소스 존재하지 않음"),
    INTERNAL_SERVER_ERROR("서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요. ", "서버 에러"),
    // authorization
    INVALID_TOKEN("유효하지 않은 토큰입니다. 다시 로그인해주세요. ", "사용자 권한 확인 실패"),
    FORBIDDEN("권한이 없습니다. ", "권한 없음"),
    // user
    USER_NOT_FOUND("사용자가 존재하지 않습니다. ", "사용자 조회 실패"),
    USER_NICKNAME_DUPLICATED("이미 사용중인 닉네임입니다. 다른 닉네임으로 다시 시도해주세요. ", "닉네임 중복"),
    USER_INVALID_STATUS_WITHDRAWAL("탈퇴한 사용자입니다. ", "회원 탈퇴"),
    // store
    STORE_NOT_FOUND("가게가 존재하지 않습니다. ", "가게 조회 실패"),
    STORE_DELETE_REQUEST_DUPLICATED("이미 삭제 요청한 가게입니다. ", "삭제 요청 중복"),
    // review
    REVIEW_NOT_FOUND("리뷰가 존재하지 않습니다. ", "리뷰 조회 실패"),
    // faq
    FAQ_NOT_FOUND("FAQ 가 존재하지 않습니다. ",  "FAQ 조회 실패"),
    // faq tag
    FAQ_TAG_NOT_FOUND("FAQ 태그가 존재하지 않습니다. ", "FAQ 태그 조회 실패"),
    FAQ_TAG_NAME_DUPLICATED("이미 사용중인 태그 이름입니다. 다른 이름으로 다시 시도해주세요. ", "태그 이름 중복"),

    ;

    private final String defaultMessage;
    private final String description;

    ResultCode(String defaultErrorMessage, String description) {
        this.defaultMessage = defaultErrorMessage;
        this.description = description;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}
