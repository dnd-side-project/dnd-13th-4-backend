package com.example.wini.infra.apple.config;

import static com.example.wini.global.common.constant.OauthConstants.APPLE_PUBLIC_KEYS_URL;

import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppleJwkConfig {

    @Bean
    public JwkProvider appleJwkProvider() throws MalformedURLException {
        return new JwkProviderBuilder(new URL(APPLE_PUBLIC_KEYS_URL))
                .cached(5, 24, TimeUnit.HOURS) // 24시간 동안 캐싱
                .rateLimited(10, 1, TimeUnit.MINUTES) // 1분 동안 최대 10번만 요청 (캐시에 값이 없을 경우)
                .build();
    }
}
