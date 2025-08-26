package com.example.wini.domain.member.repository;

import com.example.wini.domain.member.domain.Member;
import com.example.wini.domain.member.dto.query.MateInfoQuery;
import java.util.Optional;

public interface MemberCustomRepository {
    Optional<Member> findWithStatusByMemberId(Long memberId);

    Optional<Member> findRoommateWithStatusByMemberId(Long memberId);

    Optional<Member> findRoommateByMemberId(Long memberId);

    Optional<MateInfoQuery> findRoommateWithJoinedAtByMemberId(Long memberId);
}
