package com.example.wini.global.config;

import com.example.wini.infra.kakao.client.KakaoClient;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestClient;

@TestConfiguration
@Profile("test")
public class TestConfig {
    @MockBean
    private RestClient restClient;

    @Bean
    public KakaoClient kakaoClient() {
        return new KakaoClient(restClient);
    }
}
