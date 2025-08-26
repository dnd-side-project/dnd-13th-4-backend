package com.example.wini.domain.auth.controller;

import com.example.wini.domain.auth.dto.response.TokenResponse;
import com.example.wini.domain.auth.service.RefreshTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "8. 인증 인가", description = "인증인가 관련 API")
@RequiredArgsConstructor
public class RefreshTokenController {

    private final RefreshTokenService refreshTokenService;

    @PostMapping("/reissue")
    @Operation(summary = "토큰 재발급", description = "유효한 토큰을 반환합니다.")
    public TokenResponse reissueToken(@RequestHeader("Authorization") String refreshTokenHeader) {
        return refreshTokenService.reissueToken(refreshTokenHeader);
    }
}
