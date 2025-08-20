package com.example.wini.domain.room.dto.response;

import com.example.wini.domain.room.entity.Room;

public record RoomResponse(Long id, String code) {
    public static RoomResponse from(Room room) {
        return new RoomResponse(room.getId(), room.getRoomCode());
    }
}
