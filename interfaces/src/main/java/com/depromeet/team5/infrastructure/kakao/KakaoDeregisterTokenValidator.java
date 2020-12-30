package com.depromeet.team5.infrastructure.kakao;

import com.depromeet.team5.application.security.TokenValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.regex.Pattern;

@Slf4j
@Component
public class KakaoDeregisterTokenValidator implements TokenValidator {
    private static final Pattern KAKAO_TOKEN_FORMAT = Pattern.compile("^KakaoAK (\\.*)$");

    @Value("${kakao.key.admin}")
    private String kakaoAdminKey;

    @Override
    public boolean supports(String token) {
        return token != null && KAKAO_TOKEN_FORMAT.matcher(token).matches();
    }

    @Override
    public boolean isValid(String token) {
        if (token == null) {
            return false;
        }
        final String adminKeyInToken;
        try {
            adminKeyInToken = KAKAO_TOKEN_FORMAT.matcher(token).group(1);
        } catch (IllegalArgumentException ignore) {
            return false;
        }
        return Objects.equals(adminKeyInToken, kakaoAdminKey);
    }
}
