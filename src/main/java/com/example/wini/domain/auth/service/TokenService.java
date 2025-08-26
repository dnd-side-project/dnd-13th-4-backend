package com.example.wini.domain.auth.service;

import static com.example.wini.global.common.constant.SecurityConstants.TOKEN_BLACKLIST_CACHE_NAME;

import com.example.wini.domain.auth.domain.RefreshToken;
import com.example.wini.domain.auth.dto.response.TokenResponse;
import com.example.wini.domain.auth.repository.RefreshTokenRepository;
import com.example.wini.domain.common.util.MemberUtil;
import com.example.wini.domain.member.domain.Member;
import com.example.wini.global.security.AuthMember;
import com.example.wini.global.security.JwtProvider;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;
    private final MemberUtil memberUtil;

    @Transactional
    public TokenResponse upsertTokens(Member member) {
        String newAccessToken = jwtProvider.generateAccessToken(member);
        String newRefreshToken = jwtProvider.generateRefreshToken(member);

        Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findByMemberId(member.getId());
        if (optionalRefreshToken.isPresent()) {
            RefreshToken storedToken = optionalRefreshToken.get();
            storedToken.updateToken(newRefreshToken);
        } else {
            RefreshToken newRefreshTokenEntity = RefreshToken.create(member, newRefreshToken);
            refreshTokenRepository.save(newRefreshTokenEntity);
        }

        return TokenResponse.of(newAccessToken, newRefreshToken);
    }

    @Transactional
    public void deleteToken(String accessToken, AuthMember authMember) {
        Member member = memberUtil.getCurrentMember(authMember);
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
