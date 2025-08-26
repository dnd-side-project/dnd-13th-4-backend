package com.example.wini.domain.member.dto.response;

import com.example.wini.domain.member.domain.Member;

public record MemberResponse(Long id, String name, String email, String image, boolean isMatched) {
    public static MemberResponse from(Member member, boolean isMatched) {
        return new MemberResponse(member.getId(), member.getName(), member.getEmail(), member.getImage(), isMatched);
    }
}
