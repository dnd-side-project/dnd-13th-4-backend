package com.example.wini.infra.apple.dto;

import static com.example.wini.global.error.exception.ErrorCode.APPLE_PUBLIC_KEY_NOT_FOUND;

import com.example.wini.global.error.exception.CustomException;
import java.util.List;

public record ApplePublicKeyResponse(List<Key> keys) {
    public record Key(String kty, String kid, String use, String alg, String n, String e) {}

    public Key findMatchedPublicKey(String kid, String alg) {
        return keys.stream()
                .filter(key -> key.kid().equals(kid) && key.alg().equals(alg))
                .findFirst()
                .orElseThrow(() -> new CustomException(APPLE_PUBLIC_KEY_NOT_FOUND));
    }
}
