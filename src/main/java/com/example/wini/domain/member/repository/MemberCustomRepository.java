package com.example.wini.domain.member.repository;

import com.example.wini.domain.member.domain.Member;
import java.util.Optional;

public interface MemberCustomRepository {
    Optional<Member> findWithStatusByMemberId(Long memberId);

    Optional<Member> findRoommateWithStatusByMemberId(Long memberId);

    Optional<Member> findRoommateByMemberId(Long memberId);
}
