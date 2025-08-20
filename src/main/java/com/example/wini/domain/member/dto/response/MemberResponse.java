package com.example.wini.domain.member.dto.response;

import com.example.wini.domain.member.domain.Member;

// TODO: 프로필 이미지를 보여주는 곳이 추후 생기는 건지 논의 필요
public record MemberResponse(Long id, String name, String email) {
    public static MemberResponse from(Member member) {
        return new MemberResponse(member.getId(), member.getName(), member.getEmail());
    }
}
