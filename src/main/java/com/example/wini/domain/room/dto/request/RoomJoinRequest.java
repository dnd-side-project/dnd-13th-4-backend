package com.example.wini.domain.room.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RoomJoinRequest(@NotBlank String roomCode) {}
