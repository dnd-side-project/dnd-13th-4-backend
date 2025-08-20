package com.example.wini.domain.room.repository;

import com.example.wini.domain.room.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long>, RoomCustomRepository {

    boolean existsByRoomCode(String roomCode);
}
