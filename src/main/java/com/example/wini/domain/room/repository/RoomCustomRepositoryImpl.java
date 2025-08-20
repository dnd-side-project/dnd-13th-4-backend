package com.example.wini.domain.room.repository;

import static com.example.wini.domain.room.entity.QMemberRoom.memberRoom;
import static com.example.wini.domain.room.entity.QRoom.room;

import com.example.wini.domain.room.entity.Room;
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

    @Override
    public Optional<Room> findOpenRoomByRoomCode(String roomCode) {
        return Optional.ofNullable(queryFactory
                .selectFrom(room)
                .where(room.roomCode.eq(roomCode).and(room.isClosed.isFalse()))
                .fetchOne());
    }

    @Override
    public boolean existsOpenRoomByMemberId(Long memberId) {
        return queryFactory
                        .selectOne()
                        .from(room)
                        .join(memberRoom)
                        .on(memberRoom.room.id.eq(room.id))
                        .where(memberRoom.member.id.eq(memberId).and(room.isClosed.isFalse()))
                        .fetchFirst()
                != null;
    }
}
