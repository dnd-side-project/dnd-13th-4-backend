package com.example.wini.domain.member.controller;

import com.example.wini.domain.common.annotation.Auth;
import com.example.wini.domain.member.dto.request.MemberStatusUpdateRequest;
import com.example.wini.domain.member.dto.response.MemberResponse;
import com.example.wini.domain.member.dto.response.MemberStatusResponse;
import com.example.wini.domain.member.service.MemberService;
import com.example.wini.global.security.AuthMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "1. 회원 관리", description = "회원 관련 API")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/me/status")
    @Operation(summary = "현재 내 상태", description = "현재 내 상태를 반환합니다. 현재 상태가 없을 시에는 각 필드가 Null로 반환됩니다.")
    public MemberStatusResponse getMyStatus(@Auth AuthMember authMember) {
        return memberService.searchMyStatus(authMember);
    }

    @GetMapping("/mate/status")
    @Operation(summary = "현재 룸메 상태", description = "현재 룸메 상태를 반환합니다. 현재 상태가 없을 시에는 각 필드가 Null로 반환됩니다.")
    public MemberStatusResponse getMateStatus(@Auth AuthMember authMember) {
        return memberService.searchMateStatus(authMember);
    }

    @PutMapping("/me/status")
    @Operation(summary = "내 상태 수정", description = "수정한 상태를 반환합니다. '계속 유지'의 경우 시간과 분은 -1로 입력해주세요.")
    public MemberStatusResponse putStatus(
            @Auth AuthMember authMember, @Valid @RequestBody MemberStatusUpdateRequest request) {
        return memberService.updateStatus(authMember, request);
    }

    @GetMapping("/me")
    @Operation(summary = "내 정보 조회", description = "내 정보를 반환합니다.")
    public MemberResponse getMyInfo(@Auth AuthMember authMember) {
        return memberService.getMyInfo(authMember);
    }
}
