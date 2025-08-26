package com.example.wini.domain.auth.service;

import com.example.wini.domain.auth.domain.RefreshToken;
import com.example.wini.domain.auth.dto.response.TokenResponse;
import com.example.wini.domain.auth.repository.RefreshTokenRepository;
import com.example.wini.domain.member.domain.Member;
import com.example.wini.global.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    public TokenResponse generateToken(Member member) {
        String newAccessToken = jwtProvider.generateAccessToken(member);
        String newRefreshToken = jwtProvider.generateRefreshToken(member);

        RefreshToken refreshToken = RefreshToken.create(member, jwtProvider.substringToken(newRefreshToken));
        refreshTokenRepository.save(refreshToken);

        return TokenResponse.of(newAccessToken, newRefreshToken);
    }
}
