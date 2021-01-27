package com.depromeet.team5.infrastructure.kakao;

import com.depromeet.team5.application.security.TokenValidator;
import com.google.common.net.HttpHeaders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class KakaoLoginTokenValidator implements TokenValidator {

    private final WebClient webClient;

    @Override
    public boolean supports(String accessToken) {
        return false;
    }

    @Override
    public boolean isValid(String accessToken) {

        ClientResponse clientResponse = webClient.mutate()
                 .baseUrl("https://kapi.kakao.com")
                 .build()
                 .get()
                 .uri("/v1/user/access_token_info")
                 .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                 .exchange()
                 .block();

        return clientResponse != null && clientResponse.statusCode().is2xxSuccessful();
    }
}
