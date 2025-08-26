package com.example.wini.domain.auth.service;

import static com.example.wini.global.common.constant.SecurityConstants.REFRESH_TOKEN;
import static com.example.wini.global.common.constant.SecurityConstants.TOKEN_BLACKLIST_CACHE_NAME;
import static com.example.wini.global.error.exception.ErrorCode.INVALID_REFRESH_TOKEN;
import static com.example.wini.global.error.exception.ErrorCode.MEMBER_NOT_FOUND;

import com.example.wini.domain.auth.domain.RefreshToken;
import com.example.wini.domain.auth.dto.response.TokenResponse;
import com.example.wini.domain.auth.repository.RefreshTokenRepository;
import com.example.wini.domain.common.util.MemberUtil;
import com.example.wini.domain.member.domain.Member;
import com.example.wini.domain.member.repository.MemberRepository;
import com.example.wini.global.error.exception.CustomException;
import com.example.wini.global.security.AuthMember;
import com.example.wini.global.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;
    private final MemberUtil memberUtil;

    @Transactional
    public TokenResponse generateTokens(Member member) {
        String accessToken = jwtProvider.generateAccessToken(member);
        String refreshToken = jwtProvider.generateRefreshToken(member);

        RefreshToken newRefreshTokenEntity = RefreshToken.create(member, jwtProvider.substringToken(refreshToken));
        refreshTokenRepository.save(newRefreshTokenEntity);

        return TokenResponse.of(accessToken, refreshToken);
    }

    @Transactional
    public TokenResponse reissueToken(String refreshTokenHeader) {
        String refreshToken = validateRefreshToken(refreshTokenHeader);

        return updateAndGenerateTokens(refreshToken);
    }

    private String validateRefreshToken(String refreshTokenHeader) {
        String refreshToken = jwtProvider.substringToken(refreshTokenHeader);

        if (!jwtProvider.validToken(refreshToken, REFRESH_TOKEN)) {
            throw new CustomException(INVALID_REFRESH_TOKEN);
        }
        return refreshToken;
    }

    private TokenResponse updateAndGenerateTokens(String refreshToken) {
        Long userId = jwtProvider.getMemberId(refreshToken, REFRESH_TOKEN);

        Member member = memberRepository.findById(userId).orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        RefreshToken storedRefreshToken = refreshTokenRepository
                .findByToken(refreshToken)
                .orElseThrow(() -> new CustomException(INVALID_REFRESH_TOKEN));

        String newAccessToken = jwtProvider.generateAccessToken(member);
        String newRefreshToken = jwtProvider.generateRefreshToken(member);

        storedRefreshToken.updateToken(newRefreshToken);
        refreshTokenRepository.save(storedRefreshToken);

        return TokenResponse.of(newAccessToken, newRefreshToken);
    }

    @Transactional
    public void deleteToken(String accessToken, AuthMember authMember) {
        Member member = memberUtil.getMember(authMember);
        addAccessTokenToBlacklist(accessToken);
        refreshTokenRepository.deleteByMember(member);
    }

    @CachePut(value = TOKEN_BLACKLIST_CACHE_NAME, key = "#accessToken")
    public boolean addAccessTokenToBlacklist(String accessToken) {
        return true;
    }

    @Cacheable(value = TOKEN_BLACKLIST_CACHE_NAME, key = "#accessToken", unless = "#result == false")
    public boolean isAccessTokenBlacklisted(String accessToken) {
        return false;
    }
}
