package com.example.wini.domain.room.repository;

import com.example.wini.domain.room.entity.Room;
import java.util.Optional;

public interface RoomCustomRepository {
   Optional<Long> findOpenRoomIdByMemberId(Long memberId);

  Optional<Room> findOpenRoomByRoomCode(String roomCode);

  boolean existsOpenRoomByMemberId(Long memberId);
}
