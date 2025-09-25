package com.example.wini.infra.apple.service;

import static com.example.wini.global.common.constant.OauthConstants.APPLE_ISSUER_URL;
import static com.example.wini.global.error.exception.ErrorCode.INVALID_ID_TOKEN;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.wini.domain.auth.dto.common.OauthMemberInfo;
import com.example.wini.global.error.exception.CustomException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppleOauthService {

    @Value("${apple.client-id}")
    private String clientId;

    private final JwkProvider appleJwkProvider;

    public OauthMemberInfo parseMemberInfo(String idToken) {
        DecodedJWT jwt = verifyIdToken(idToken);

        String providerId = jwt.getSubject();
        String name = jwt.getClaim("name").asString();
        if (name == null || name.isBlank()) {
            name = "위니";
        }

        return OauthMemberInfo.of(providerId, name);
    }

    private DecodedJWT verifyIdToken(String idToken) {
        try {
            log.info(idToken);
            DecodedJWT decoded = JWT.decode(idToken);
            String kid = decoded.getKeyId();

            Jwk jwk = appleJwkProvider.get(kid);
            PublicKey publicKey = jwk.getPublicKey();

            Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) publicKey, null);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(APPLE_ISSUER_URL)
                    .withAudience(clientId)
                    .build();

            return verifier.verify(idToken);

        } catch (Exception e) {
            throw new CustomException(INVALID_ID_TOKEN);
        }
    }
}
