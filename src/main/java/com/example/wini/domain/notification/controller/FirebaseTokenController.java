package com.example.wini.domain.notification.controller;

import com.example.wini.domain.notification.dto.request.FirebaseTokenSaveRequest;
import com.example.wini.domain.notification.service.FirebaseTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
@Tag(name = "7. 알림 관리", description = "알림 관련 API")
@RequiredArgsConstructor
public class FirebaseTokenController {

    private static final long MEMBER_ID = 1L;

    private final FirebaseTokenService firebaseTokenService;

    @PostMapping("/tokens")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "알림 토큰 저장", description = "FCM 토큰을 저장합니다.")
    public void saveFirebaseToken(
            // TODO: 사용자 정보
            @Valid @RequestBody FirebaseTokenSaveRequest request) {
        firebaseTokenService.saveFirebaseToken(MEMBER_ID, request);
    }
}
