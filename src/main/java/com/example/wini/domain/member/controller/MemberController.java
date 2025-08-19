package com.example.wini.domain.member.controller;

import com.example.wini.domain.member.dto.request.MemberStatusUpdateRequest;
import com.example.wini.domain.member.dto.response.MemberResponse;
import com.example.wini.domain.member.dto.response.MemberStatusResponse;
import com.example.wini.domain.member.service.MemberService;
import com.example.wini.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

  @Operation(
      summary = "현재 룸메 상태",
      description = "현재 룸메 상태를 반환합니다. 현재 상태가 없을 시에는 각 필드가 Null로 반환됩니다.")
  @GetMapping("/mate/status")
  public ResponseEntity<ApiResponse<MemberStatusResponse>> getMateStatus(
      // TODO: 토큰이 생기면 사용자 정보 추출하기
      ) {
    MemberStatusResponse response = memberService.searchMateStatus();
    return ResponseEntity.ok(ApiResponse.success(response));
  }

  @Operation(summary = "내 상태 수정", description = "수정한 상태를 반환합니다. '계속 유지'의 경우 시간과 분은 -1로 입력해주세요.")
  @PutMapping("/me/status")
  public ResponseEntity<ApiResponse<MemberStatusResponse>> putStatus(
      // TODO: 토큰이 생기면 사용자 정보 추출하기
      @Valid @RequestBody MemberStatusUpdateRequest request) {
    MemberStatusResponse response = memberService.updateStatus(request);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

  @Operation(summary = "내 정보 조회", description = "내 정보를 반환합니다.")
  @GetMapping("/me")
  public ResponseEntity<ApiResponse<MemberResponse>> getMyInfo(
      // TODO: 토큰이 생기면 사용자 정보 추출하기
      ) {
    MemberResponse response = memberService.getMyInfo();
    return ResponseEntity.ok(ApiResponse.success(response));
  }
}
