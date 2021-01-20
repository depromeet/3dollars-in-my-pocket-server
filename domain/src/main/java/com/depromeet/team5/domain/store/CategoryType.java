package com.depromeet.team5.domain.store;

import lombok.RequiredArgsConstructor;

/**
 * 이름은 로마자 표기법을 사용합니다.
 * 초기값(붕어빵, 타코야끼, 계란빵, 호떡) 과의 일관성을 위해 로마자 표기법을 사용합니다.
 * 단, 외국어 및 알파벳으로 표기 가능한 외래어는 원래 이름을 그대로 사용합니다.
 * `@Enumerated(EnumType.STRING)` 으로 저장되어있어서 기존 값 migration 하는 것은 까다로움.
 */
@RequiredArgsConstructor
public enum CategoryType {
    BUNGEOPPANG("붕어빵"),
    TAKOYAKI("타코야끼"),
    GYERANPPANG("계란빵"),
    HOTTEOK("호떡"),
    EOMUK("어묵"),
    GUNGOGUMA("군고구마"),
    TTEOKBOKI("떡볶이"),
    TTANGKONGPPANG("땅콩빵"),
    GUNOKSUSU("군옥수수"),
    DAKKKOCHI("닭꼬치"),
    TOAST("토스트"),
    WAFFLE("와플"),
    GUKHWAPPANG("국화빵"),
    SUNDAE("순대"),
    ;

    private final String category;
}
