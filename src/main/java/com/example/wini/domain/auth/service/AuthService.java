package com.example.wini.domain.auth.service;

import static com.example.wini.domain.member.domain.OauthProvider.KAKAO;

import com.example.wini.domain.auth.dto.common.OauthMemberInfo;
import com.example.wini.domain.auth.dto.response.TokenResponse;
import com.example.wini.domain.member.domain.Member;
import com.example.wini.domain.member.repository.MemberRepository;
import com.example.wini.infra.kakao.client.KakaoClient;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final KakaoClient kakaoClient;
    private final MemberRepository memberRepository;
    private final RefreshTokenService refreshTokenService;

    @Transactional(readOnly = true)
    public String getKakaoLoginForm() {
        return kakaoClient.getAuthorizationCodeUri();
    }

    @Transactional
    public TokenResponse kakaoLogin(String authCode) {
        String kakaoAccessToken = kakaoClient.getAccessToken(authCode);
        OauthMemberInfo oauthMemberInfo = kakaoClient.getMemberInfo(kakaoAccessToken);

        Optional<Member> optionalMember = memberRepository.findByOauthId(oauthMemberInfo.providerId());

        Member member = optionalMember.orElseGet(() -> {
            Member newMember = Member.create(oauthMemberInfo, KAKAO);
            return memberRepository.save(newMember);
        });

        return refreshTokenService.generateTokens(member);
    }
}
