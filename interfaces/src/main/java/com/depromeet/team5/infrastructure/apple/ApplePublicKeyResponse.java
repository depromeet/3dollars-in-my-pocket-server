package com.depromeet.team5.infrastructure.apple;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
@Data
public class ApplePublicKeyResponse {

    private List<Key> keys;

    @Data
    public static class Key {

        private String kty;
        private String kid;
        private String use;
        private String alg;
        private String n;
        private String e;
    }

    public Optional<Key> getMatchedKey(String kid, String alg) {
        return this.keys.stream()
                .filter(key -> key.getKid().equals(kid) && key.getAlg().equals(alg))
                .findFirst();
    }
}
