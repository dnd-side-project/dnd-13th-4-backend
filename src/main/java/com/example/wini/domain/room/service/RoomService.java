package com.example.wini.domain.room.service;

import static com.example.wini.global.error.exception.ErrorCode.ALREADY_JOIN_ROOM;
import static com.example.wini.global.error.exception.ErrorCode.MEMBER_NOT_FOUND;
import static com.example.wini.global.error.exception.ErrorCode.ROOM_IS_FULL;

import com.example.wini.domain.member.domain.Member;
import com.example.wini.domain.member.repository.MemberRepository;
import com.example.wini.domain.room.dto.response.RoomResponse;
import com.example.wini.domain.room.entity.MemberRoom;
import com.example.wini.domain.room.entity.Room;
import com.example.wini.domain.room.repository.MemberRoomRepository;
import com.example.wini.domain.room.repository.RoomRepository;
import com.example.wini.global.error.exception.CustomException;
import java.security.SecureRandom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoomService {

    private static final Integer ROOM_CODE_LENGTH = 7;
    private static final Integer ROOM_MEMBER_MAX_COUNT = 2;

    private final MemberRepository memberRepository;
    private final RoomRepository roomRepository;
    private final MemberRoomRepository memberRoomRepository;

    @Transactional
    public RoomResponse createRoom(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        String roomCode = generateUniqueRoomCode();

        Room room = Room.create(roomCode);
        Room saveRoom = roomRepository.save(room);

        MemberRoom memberRoom = MemberRoom.create(member, room);
        memberRoomRepository.save(memberRoom);

        return RoomResponse.from(saveRoom);
    }

    private String generateUniqueRoomCode() {
        String roomCode;
        do {
            roomCode = generateRoomCode();
        } while (roomRepository.existsByRoomCode(roomCode));
        return roomCode;
    }

    private String generateRoomCode() {
        String charSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder codeBuilder = new StringBuilder(ROOM_CODE_LENGTH);

        for (int i = 0; i < ROOM_CODE_LENGTH; i++) {
            int index = random.nextInt(charSet.length());
            codeBuilder.append(charSet.charAt(index));
        }
        return codeBuilder.toString();
    }

    private void validateMemberCanJoinRoom(Long memberId) {
        boolean isAlreadyJoined = roomRepository.existsOpenRoomByMemberId(memberId);
        if (isAlreadyJoined) {
            throw new CustomException(ALREADY_JOIN_ROOM);
        }
    }

   private void validateRoomCapacity(Room room) {
        long memberCount = memberRoomRepository.countMembersByRoomId(room.getId());
        if (memberCount >= ROOM_MEMBER_MAX_COUNT) {
            throw new CustomException(ROOM_IS_FULL);
        }
    }
}
