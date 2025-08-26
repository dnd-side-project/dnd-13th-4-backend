package com.example.wini.global.security;

import static com.example.wini.global.common.constant.SecurityConstants.ACCESS_TOKEN;
import static com.example.wini.global.common.constant.SecurityConstants.BEARER_TOKEN_PREFIX;
import static com.example.wini.global.common.constant.SecurityConstants.JWT_TYPE;
import static com.example.wini.global.error.exception.ErrorCode.EXPIRED_TOKEN;
import static com.example.wini.global.error.exception.ErrorCode.INTERNAL_SERVER_ERROR;
import static com.example.wini.global.error.exception.ErrorCode.INVALID_TOKEN_SIGNATURE;
import static com.example.wini.global.error.exception.ErrorCode.MALFORMED_TOKEN;
import static com.example.wini.global.error.exception.ErrorCode.TOKEN_REQUIRED;
import static com.example.wini.global.error.exception.ErrorCode.UNSUPPORTED_TOKEN;

import com.example.wini.domain.member.domain.Member;
import com.example.wini.global.error.exception.CustomException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.DecodingException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.accessToken.secret-key}")
    private String accessTokenSecret;

    @Value("${jwt.accessToken.expiration-time}")
    private Long accessTokenExpiration;

    @Value("${jwt.refreshToken.secret-key}")
    private String refreshTokenSecret;

    @Value("${jwt.refreshToken.expiration-time}")
    private Long refreshTokenExpiration;

    private SecretKey accessTokenKey;
    private SecretKey refreshTokenKey;

    @PostConstruct
    public void init() {
        this.accessTokenKey = Keys.hmacShaKeyFor(accessTokenSecret.getBytes());
        this.refreshTokenKey = Keys.hmacShaKeyFor(refreshTokenSecret.getBytes());
    }

    public String generateAccessToken(Member member) {
        Date now = new Date();
        return BEARER_TOKEN_PREFIX
                + Jwts.builder()
                        .header()
                        .type(JWT_TYPE)
                        .and()
                        .issuer(issuer)
                        .subject(String.valueOf(member.getId()))
                        .issuedAt(now)
                        .expiration(new Date(now.getTime() + accessTokenExpiration * 1000))
                        .signWith(accessTokenKey)
                        .compact();
    }

    public String generateRefreshToken(Member member) {
        Date now = new Date();
        return BEARER_TOKEN_PREFIX
                + Jwts.builder()
                        .header()
                        .type(JWT_TYPE)
                        .and()
                        .issuer(issuer)
                        .subject(String.valueOf(member.getId()))
                        .issuedAt(now)
                        .expiration(new Date(now.getTime() + refreshTokenExpiration * 1000))
                        .signWith(refreshTokenKey)
                        .compact();
    }

    public String substringToken(String tokenValue) {
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_TOKEN_PREFIX)) {
            return tokenValue.substring(BEARER_TOKEN_PREFIX.length());
        }
        throw new CustomException(TOKEN_REQUIRED);
    }

    public boolean validToken(String token, String tokenType) {
        try {
            getMemberInfoFromToken(token, tokenType);
            return true;
        } catch (SecurityException e) {
            throw new JwtAuthenticationException(INVALID_TOKEN_SIGNATURE);
        } catch (ExpiredJwtException e) {
            throw new JwtAuthenticationException(EXPIRED_TOKEN);
        } catch (UnsupportedJwtException e) {
            throw new JwtAuthenticationException(UNSUPPORTED_TOKEN);
        } catch (DecodingException | MalformedJwtException e) {
            throw new JwtAuthenticationException(MALFORMED_TOKEN);
        } catch (Exception e) {
            throw new JwtAuthenticationException(INTERNAL_SERVER_ERROR);
        }
    }

    public AuthMember getAuthentication(String token, String tokenType) {
        Claims claims = getMemberInfoFromToken(token, tokenType);
        Long memberId = Long.parseLong(claims.getSubject());
        return AuthMember.of(memberId);
    }

    private Claims getMemberInfoFromToken(String token, String tokenType) {
        SecretKey key = ACCESS_TOKEN.equals(tokenType) ? accessTokenKey : refreshTokenKey;

        return Jwts.parser()
                .verifyWith(key)
                .requireIssuer(issuer)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
