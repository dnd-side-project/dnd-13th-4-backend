package com.example.wini.domain.room.repository;

import java.util.Optional;

public interface RoomCustomRepository {
  Optional<Long> findOpenRoomIdByMemberId(Long memberId);
}
