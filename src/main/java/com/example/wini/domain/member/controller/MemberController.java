package com.example.wini.domain.member.controller;

import com.example.wini.domain.member.dto.response.MemberStatusResponse;
import com.example.wini.domain.member.service.MemberService;
import com.example.wini.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "1. 회원 관리", description = "회원 관련 API")
@RequiredArgsConstructor
public class MemberController {

  private final MemberService memberService;

  @Operation(summary = "현재 내 상태", description = "현재 내 상태를 반환합니다. 현재 상태가 없을 시에는 각 필드가 Null로 반환됩니다.")
  @GetMapping("/me/status")
  public ResponseEntity<ApiResponse<MemberStatusResponse>> getMyStatus(
      // TODO: 토큰이 생기면 사용자 정보 추출하기
      ) {
    MemberStatusResponse response = memberService.searchMyStatus();
    return ResponseEntity.ok(ApiResponse.success(response));
  }
}
