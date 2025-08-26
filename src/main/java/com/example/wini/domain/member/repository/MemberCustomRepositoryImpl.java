package com.example.wini.domain.member.repository;

import static com.example.wini.domain.member.domain.QMember.member;

import com.example.wini.domain.member.domain.Member;
import com.example.wini.domain.member.dto.query.MateInfoQuery;
import com.example.wini.domain.room.entity.QMemberRoom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberCustomRepositoryImpl implements MemberCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Member> findWithStatusByMemberId(Long memberId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(member)
                .leftJoin(member.status)
                .fetchJoin()
                .where(member.id.eq(memberId))
                .fetchOne());
    }

    @Override
    public Optional<Member> findRoommateWithStatusByMemberId(Long memberId) {
        QMemberRoom myMemberRoom = new QMemberRoom("mr1");
        QMemberRoom mateMemberRoom = new QMemberRoom("mr2");

        return Optional.ofNullable(queryFactory
                .select(mateMemberRoom.member)
                .from(myMemberRoom)
                .join(mateMemberRoom)
                .on(myMemberRoom.room.eq(mateMemberRoom.room))
                .leftJoin(mateMemberRoom.member.status)
                .fetchJoin()
                .where(
                        myMemberRoom.member.id.eq(memberId),
                        mateMemberRoom.member.id.ne(memberId),
                        myMemberRoom.room.isClosed.isFalse())
                .fetchOne());
    }

    @Override
    public Optional<Member> findRoommateByMemberId(Long memberId) {
        QMemberRoom myMemberRoom = new QMemberRoom("mr1");
        QMemberRoom mateMemberRoom = new QMemberRoom("mr2");

        return Optional.ofNullable(queryFactory
                .select(mateMemberRoom.member)
                .from(myMemberRoom)
                .join(mateMemberRoom)
                .on(myMemberRoom.room.eq(mateMemberRoom.room))
                .where(
                        myMemberRoom.member.id.eq(memberId),
                        mateMemberRoom.member.id.ne(memberId),
                        myMemberRoom.room.isClosed.isFalse())
                .fetchOne());
    }

    @Override
    public Optional<MateInfoQuery> findRoommateWithJoinedAtByMemberId(Long memberId) {
        QMemberRoom myMemberRoom = new QMemberRoom("mr1");
        QMemberRoom mateMemberRoom = new QMemberRoom("mr2");

        MateInfoQuery result = queryFactory
                .select(Projections.constructor(
                        MateInfoQuery.class,
                        mateMemberRoom.member.id,
                        mateMemberRoom.member.name,
                        mateMemberRoom.member.image,
                        mateMemberRoom.createdAt))
                .from(myMemberRoom)
                .join(mateMemberRoom)
                .on(myMemberRoom.room.eq(mateMemberRoom.room))
                .where(
                        myMemberRoom.member.id.eq(memberId),
                        mateMemberRoom.member.id.ne(memberId),
                        myMemberRoom.room.isClosed.isFalse())
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
