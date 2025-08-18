package com.example.wini.domain.member.repository;

import static com.example.wini.domain.member.domain.QMember.member;

import com.example.wini.domain.member.domain.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberCustomRepositoryImpl implements MemberCustomRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public Optional<Member> findWithStatusByMemberId(Long memberId) {
    return Optional.ofNullable(
        queryFactory
            .selectFrom(member)
            .join(member.status)
            .fetchJoin()
            .where(member.id.eq(memberId))
            .fetchOne());
  }
}
