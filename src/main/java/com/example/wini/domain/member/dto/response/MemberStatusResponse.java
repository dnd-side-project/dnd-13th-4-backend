package com.example.wini.domain.member.dto.response;

import com.example.wini.domain.member.domain.Member;
import com.example.wini.domain.member.dto.common.ReservedTimeInfo;
import java.time.LocalDateTime;

public record MemberStatusResponse(
        String emoji, String text, LocalDateTime statusStartedAt, ReservedTimeInfo reservedTimeInfo) {

    public static MemberStatusResponse from(Member member, ReservedTimeInfo reservedTimeInfo) {
        return new MemberStatusResponse(
                member.getStatus().getEmoji(),
                member.getStatus().getText(),
                member.getStatusStartedAt(),
                reservedTimeInfo);
    }

    public static MemberStatusResponse empty() {
        return new MemberStatusResponse(null, null, null, null);
    }
}
