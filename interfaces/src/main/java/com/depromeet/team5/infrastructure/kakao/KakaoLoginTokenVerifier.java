package com.depromeet.team5.infrastructure.kakao;

import com.depromeet.team5.application.security.TokenValidator;
import com.depromeet.team5.application.security.TokenVerifier;
import com.google.common.net.HttpHeaders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class KakaoLoginTokenVerifier implements TokenVerifier {

    private final WebClient webClient;

    @Override
    public boolean isVerified(String accessToken) {

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
