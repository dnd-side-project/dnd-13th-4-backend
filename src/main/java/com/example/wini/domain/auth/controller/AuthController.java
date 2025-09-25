package com.example.wini.domain.auth.controller;

import static com.example.wini.global.common.constant.SecurityConstants.BEARER_TOKEN_PREFIX;

import com.example.wini.domain.auth.dto.request.IdTokenRequest;
import com.example.wini.domain.auth.dto.response.TokenResponse;
import com.example.wini.domain.auth.service.AuthService;
import com.example.wini.domain.auth.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "8. 인증 인가", description = "인증인가 관련 API")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final TokenService tokenService;

    @GetMapping("/login/kakao")
    @SecurityRequirements(value = {})
    @Operation(summary = "카카오 로그인", description = "카카오 로그인 페이지로 리다이렉트 됩니다.")
    public void kakaoLoginForm(HttpServletResponse response) throws IOException {
        String redirectUrl = authService.getKakaoLoginForm();
        response.sendRedirect(redirectUrl);
    }

    @GetMapping("/kakao/callback")
    @SecurityRequirements(value = {})
    @Operation(summary = "카카오 로그인 콜백", description = "카카오 로그인 후 토큰을 반환합니다.")
    public void kakaoLogin(@RequestParam("code") String authCode, HttpServletResponse response) throws IOException {
        String redirectUrl = authService.kakaoLogin(authCode);
        response.sendRedirect(redirectUrl);
    }

    @PostMapping("/login/apple")
    @SecurityRequirements
    @Operation(summary = "애플 로그인", description = "idToken으로 유저 정보 추출 후 토큰을 반환합니다.")
    public TokenResponse appleLogin(@RequestBody IdTokenRequest request) {
        return authService.appleLogin(request);
    }

    @PostMapping("logout")
    @Operation(summary = "로그아웃", description = "토큰을 무효화시킵니다.")
    public void logout(@RequestHeader("Authorization") String header) {
        String accessToken = header.substring(BEARER_TOKEN_PREFIX.length());
        tokenService.deleteToken(accessToken);
    }
}
