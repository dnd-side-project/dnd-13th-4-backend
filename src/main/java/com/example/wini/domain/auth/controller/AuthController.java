package com.example.wini.domain.auth.controller;

import com.example.wini.domain.auth.dto.response.TokenResponse;
import com.example.wini.domain.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "8. 인증 인가", description = "인증인가 관련 API")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login/kakao")
    @Operation(summary = "카카오 로그인 페이지", description = "카카오 로그인 페이지 주소를 반환합니다.")
    public String kakaoLoginForm() {
        return authService.getKakaoLoginForm();
    }

    @GetMapping("/kakao/callback")
    @Operation(summary = "카카오 로그인", description = "토큰을 반환합니다.")
    public TokenResponse kakaoLogin(@RequestParam("code") String authCode) {
        return authService.kakaoLogin(authCode);
    }
}
