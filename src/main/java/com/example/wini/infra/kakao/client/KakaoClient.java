package com.example.wini.infra.kakao.client;

import static com.example.wini.global.common.constant.OauthConstants.KAKAO_BASE_URL;
import static com.example.wini.global.error.exception.ErrorCode.KAKAO_TOKEN_ISSUANCE_FAILED;
import static com.example.wini.global.error.exception.ErrorCode.KAKAO_USERINFO_FETCH_FAILED;

import com.example.wini.domain.auth.dto.common.OauthMemberInfo;
import com.example.wini.global.error.exception.CustomException;
import com.example.wini.infra.kakao.dto.KakaoTokenResponse;
import com.example.wini.infra.kakao.dto.KakaoUser;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class KakaoClient {

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    private final RestClient restClient;

    public String getAuthorizationCodeUri() {
        return KAKAO_BASE_URL + "/oauth/authorize" + "?response_type=code"
                + "&client_id=" + clientId
                + "&redirect_uri=" + redirectUri;
    }

    public String getAccessToken(String authCode) {
        MultiValueMap<String, String> bodyData = createTokenRequestBody(authCode);
        try {
            KakaoTokenResponse response = restClient
                    .post()
                    .uri(KAKAO_BASE_URL + "/oauth/token")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(bodyData)
                    .retrieve()
                    .body(KakaoTokenResponse.class);

            if (response == null || response.accessToken() == null) {
                throw new CustomException(KAKAO_TOKEN_ISSUANCE_FAILED);
            }

            return response.accessToken();
        } catch (Exception e) {
            throw new CustomException(KAKAO_TOKEN_ISSUANCE_FAILED);
        }
    }

    private MultiValueMap<String, String> createTokenRequestBody(String authCode) {
        MultiValueMap<String, String> bodyData = new LinkedMultiValueMap<>();
        bodyData.add("grant_type", "authorization_code");
        bodyData.add("client_id", clientId);
        bodyData.add("redirect_uri", redirectUri);
        bodyData.add("code", authCode);
        return bodyData;
    }

    public OauthMemberInfo getMemberInfo(String accessToken) {
        KakaoUser kakaoUser = restClient
                .get()
                .uri("https://kapi.kakao.com/v2/user/me")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                    String errorMessage = new String(res.getBody().readAllBytes(), StandardCharsets.UTF_8);
                    log.error(
                            "[KakaoClient] 카카오 사용자 정보 요청에 실패하였습니다. HTTP Status: {}, Body: {}",
                            res.getStatusCode(),
                            errorMessage);
                    throw new CustomException(KAKAO_USERINFO_FETCH_FAILED);
                })
                .body(KakaoUser.class);

        return OauthMemberInfo.from(kakaoUser);
    }
}
