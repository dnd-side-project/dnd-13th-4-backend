package com.example.wini.domain.auth.service;

import static com.example.wini.global.common.constant.SecurityConstants.REFRESH_TOKEN;
import static com.example.wini.global.error.exception.ErrorCode.INVALID_REFRESH_TOKEN;
import static com.example.wini.global.error.exception.ErrorCode.MEMBER_NOT_FOUND;

import com.example.wini.domain.auth.dto.response.TokenResponse;
import com.example.wini.domain.auth.repository.RefreshTokenRepository;
import com.example.wini.domain.member.domain.Member;
import com.example.wini.domain.member.repository.MemberRepository;
import com.example.wini.global.error.exception.CustomException;
import com.example.wini.global.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;
    private final TokenService tokenService;

    @Transactional
    public TokenResponse reissueToken(String refreshTokenHeader) {
        String refreshToken = jwtProvider.substringToken(refreshTokenHeader);
        validateRefreshToken(refreshToken);

        Long memberId = jwtProvider.getMemberId(refreshToken, REFRESH_TOKEN);

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
        refreshTokenRepository.findByToken(refreshToken).orElseThrow(() -> new CustomException(INVALID_REFRESH_TOKEN));

        return tokenService.upsertTokens(member);
    }

    private void validateRefreshToken(String refreshToken) {
        if (!jwtProvider.validToken(refreshToken, REFRESH_TOKEN)) {
            throw new CustomException(INVALID_REFRESH_TOKEN);
        }
    }
}
