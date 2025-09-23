package com.example.wini.domain.auth.service;

import static com.example.wini.domain.member.domain.OauthProvider.APPLE;
import static com.example.wini.domain.member.domain.OauthProvider.KAKAO;

import com.example.wini.domain.auth.dto.common.OauthMemberInfo;
import com.example.wini.domain.auth.dto.request.IdTokenRequest;
import com.example.wini.domain.auth.dto.response.TokenResponse;
import com.example.wini.domain.member.domain.Member;
import com.example.wini.domain.member.domain.OauthProvider;
import com.example.wini.domain.member.repository.MemberRepository;
import com.example.wini.infra.apple.service.AppleOauthService;
import com.example.wini.infra.kakao.client.KakaoClient;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final KakaoClient kakaoClient;
    private final AppleOauthService appleOauthService;
    private final MemberRepository memberRepository;
    private final TokenService tokenService;

    @Transactional(readOnly = true)
    public String getKakaoLoginForm() {
        return kakaoClient.getAuthorizationCodeUri();
    }

    @Transactional
    public String kakaoLogin(String authCode) {
        String kakaoAccessToken = kakaoClient.getAccessToken(authCode);
        OauthMemberInfo oauthMemberInfo = kakaoClient.getMemberInfo(kakaoAccessToken);
        TokenResponse tokenResponse = loginOrRegister(oauthMemberInfo, KAKAO);

        String redirectUrl = String.format(
                "com.kirikiri.wini://auth/callback?accessToken=%s&refreshToken=%s",
                URLEncoder.encode(tokenResponse.accessToken(), StandardCharsets.UTF_8),
                URLEncoder.encode(tokenResponse.refreshToken(), StandardCharsets.UTF_8));

        return redirectUrl;
    }

    @Transactional
    public TokenResponse appleLogin(IdTokenRequest request) {
        String idToken = request.idToken();
        OauthMemberInfo oauthMemberInfo = appleOauthService.parseMemberInfo(idToken);
        return loginOrRegister(oauthMemberInfo, APPLE);
    }

    private TokenResponse loginOrRegister(OauthMemberInfo oauthMemberInfo, OauthProvider provider) {
        Member member = memberRepository
                .findByOauthIdAndOauthProvider(oauthMemberInfo.providerId(), provider)
                .orElseGet(() -> memberRepository.save(Member.create(oauthMemberInfo, provider)));

        return tokenService.upsertTokens(member);
    }
}
