package com.example.wini.domain.common.util;

import static com.example.wini.global.error.exception.ErrorCode.MEMBER_NOT_FOUND;

import com.example.wini.domain.member.domain.Member;
import com.example.wini.domain.member.repository.MemberRepository;
import com.example.wini.global.error.exception.CustomException;
import com.example.wini.global.security.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MemberUtil {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public Member getCurrentMember(AuthMember authMember) {
        return memberRepository
                .findById(authMember.memberId())
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
    }
}
