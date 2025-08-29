package com.example.wini.global.common.constant;

public class SecurityConstants {

    public static final String ACCESS_TOKEN = "accessToken";
    public static final String REFRESH_TOKEN = "refreshToken";

    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String BEARER_TOKEN_PREFIX = "Bearer ";

    public static final String[] PUBLIC_URLS = {
        "/",
        "/health",
        "/auth/kakao/**",
        "/auth/login/**",
        "/api-docs",
        "/v3/api-docs",
        "/v3/api-docs/**",
        "/swagger-ui/**",
        "/swagger-ui.html"
    };
    public static final String REISSUE_URL = "/auth/reissue";

    public static final String JWT_TYPE = "JWT";

    public static final String TOKEN_BLACKLIST_CACHE_NAME = "blacklistedTokens";

    private SecurityConstants() {}
}
