package com.depromeet.team5.infrastructure.spring;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

public class StringToEnumConverterFactory implements ConverterFactory<String, Enum<?>> {

    /**
     * string 에서 enum 으로 변환할 때, 대소문자를 구분하지 않습니다.
     */
    @Override
    public <T extends Enum<?>> Converter<String, T> getConverter(Class<T> targetType) {
        return source -> {
            for (T each : targetType.getEnumConstants()) {
                if (each.name().equalsIgnoreCase(source)) {
                    return each;
                }
            }
            throw new IllegalArgumentException("Invalid source:" + source);
        };
    }
}
