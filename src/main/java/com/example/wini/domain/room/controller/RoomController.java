package com.example.wini.domain.room.controller;

import com.example.wini.domain.room.dto.request.RoomJoinRequest;
import com.example.wini.domain.room.dto.response.RoomResponse;
import com.example.wini.domain.room.service.RoomService;
import com.example.wini.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rooms")
@Tag(name = "5. 방 관리", description = "방 관련 API")
@RequiredArgsConstructor
public class RoomController {

    private static final Long MEMBER_ID = 1L;
    private static final Long MATE_ID = 2L;

    private final RoomService roomService;

    @Operation(summary = "초대코드 생성", description = "생성된 초대코드를 반환합니다.")
    @PostMapping
    public ResponseEntity<ApiResponse<RoomResponse>> createRoom(
            // TODO: 토큰이 생기면 사용자 정보 추출하기
            ) {
        RoomResponse response = roomService.createRoom(MEMBER_ID);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(HttpStatus.CREATED, response));
    }

    @Operation(summary = "초대코드 입력", description = "초대코드와 연결된 방 정보를 반환합니다.")
    @PostMapping("/join")
    public ResponseEntity<ApiResponse<RoomResponse>> joinRoom(
        // TODO: 토큰이 생기면 사용자 정보 추출하기
        @Valid @RequestBody RoomJoinRequest request) {
        RoomResponse response = roomService.joinRoom(MATE_ID, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(HttpStatus.CREATED, response));
    }
}
