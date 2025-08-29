package com.example.wini.domain.room.controller;

import com.example.wini.domain.room.dto.request.RoomJoinRequest;
import com.example.wini.domain.room.dto.response.RoomResponse;
import com.example.wini.domain.room.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rooms")
@Tag(name = "5. 방 관리", description = "방 관련 API")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "초대코드 생성", description = "생성된 초대코드를 반환합니다.")
    public RoomResponse createRoom() {
        return roomService.createRoom();
    }

    @PostMapping("/join")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "초대코드 입력", description = "초대코드와 연결된 방 정보를 반환합니다.")
    public RoomResponse joinRoom(@Valid @RequestBody RoomJoinRequest request) {
        return roomService.joinRoom(request);
    }

    @GetMapping("/my")
    @Operation(summary = "방 정보 조회", description = "방 정보를 반환합니다.")
    public RoomResponse getRoom() {
        return roomService.searchRoom();
    }
}
