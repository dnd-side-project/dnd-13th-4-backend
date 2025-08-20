package com.example.wini.domain.room.repository;

import static com.example.wini.domain.room.entity.QMemberRoom.memberRoom;
import static com.example.wini.domain.room.entity.QRoom.room;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RoomCustomRepositoryImpl implements RoomCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Long> findOpenRoomIdByMemberId(Long memberId) {
        return Optional.ofNullable(queryFactory
                .select(room.id)
                .from(room)
                .join(memberRoom)
                .on(memberRoom.room.id.eq(room.id))
                .where(memberRoom.member.id.eq(memberId).and(room.isClosed.isFalse()))
                .fetchOne());
    }
}
