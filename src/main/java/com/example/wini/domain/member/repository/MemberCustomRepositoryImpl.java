package com.example.wini.domain.member.repository;

import static com.example.wini.domain.member.domain.QMember.member;
import static com.example.wini.domain.room.entity.QMemberRoom.memberRoom;

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

  @Override
  public Optional<Member> findRoommateInMyRoom(Long memberId, Long roomId) {
    return Optional.ofNullable(
        queryFactory
            .selectFrom(member)
            .join(memberRoom)
            .on(memberRoom.member.id.eq(member.id))
            .join(member.status)
            .fetchJoin()
            .where(memberRoom.room.id.in(roomId).and(memberRoom.member.id.ne(memberId)))
            .fetchOne());
  }
}
