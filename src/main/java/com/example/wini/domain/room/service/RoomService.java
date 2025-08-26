package com.example.wini.domain.room.service;

import static com.example.wini.global.common.constant.RoomConstants.ROOM_CODE_CHAR_SET;
import static com.example.wini.global.common.constant.RoomConstants.ROOM_CODE_LENGTH;
import static com.example.wini.global.common.constant.RoomConstants.ROOM_MEMBER_MAX_COUNT;
import static com.example.wini.global.error.exception.ErrorCode.ALREADY_JOIN_ROOM;
import static com.example.wini.global.error.exception.ErrorCode.MATE_NOT_FOUND;
import static com.example.wini.global.error.exception.ErrorCode.ROOM_IS_FULL;
import static com.example.wini.global.error.exception.ErrorCode.ROOM_NOT_FOUND;

import com.example.wini.domain.common.util.MemberUtil;
import com.example.wini.domain.member.domain.Member;
import com.example.wini.domain.member.repository.MemberRepository;
import com.example.wini.domain.notification.domain.NotificationType;
import com.example.wini.domain.notification.event.NotificationEvent;
import com.example.wini.domain.room.dto.request.RoomJoinRequest;
import com.example.wini.domain.room.dto.response.RoomResponse;
import com.example.wini.domain.room.entity.MemberRoom;
import com.example.wini.domain.room.entity.Room;
import com.example.wini.domain.room.repository.MemberRoomRepository;
import com.example.wini.domain.room.repository.RoomRepository;
import com.example.wini.global.error.exception.CustomException;
import com.example.wini.global.security.AuthMember;
import java.security.SecureRandom;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final MemberRepository memberRepository;
    private final RoomRepository roomRepository;
    private final MemberRoomRepository memberRoomRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final MemberUtil memberUtil;

    @Transactional
    public RoomResponse createRoom(AuthMember authMember) {
        Member member = memberUtil.getCurrentMember(authMember);
        validateMemberCanJoinRoom(member.getId());

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
        String charSet = ROOM_CODE_CHAR_SET;
        SecureRandom random = new SecureRandom();
        StringBuilder codeBuilder = new StringBuilder(ROOM_CODE_LENGTH);

        for (int i = 0; i < ROOM_CODE_LENGTH; i++) {
            int index = random.nextInt(charSet.length());
            codeBuilder.append(charSet.charAt(index));
        }
        return codeBuilder.toString();
    }

    @Transactional
    public RoomResponse joinRoom(AuthMember authMember, RoomJoinRequest request) {
        Member member = memberUtil.getCurrentMember(authMember);
        validateMemberCanJoinRoom(member.getId());

        Room room = roomRepository
                .findOpenRoomByRoomCode(request.roomCode())
                .orElseThrow(() -> new CustomException(ROOM_NOT_FOUND));

        validateRoomCapacity(room);

        MemberRoom memberRoom = MemberRoom.create(member, room);
        memberRoomRepository.save(memberRoom);
        notifyRoommateOfNewJoiner(member.getId());

        return RoomResponse.from(room);
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

    private void notifyRoommateOfNewJoiner(Long memberId) {
        Member mate = memberRepository
                .findRoommateByMemberId(memberId)
                .orElseThrow(() -> new CustomException(MATE_NOT_FOUND));

        NotificationEvent event = NotificationEvent.from(mate.getId(), NotificationType.NEW_ROOMMATE);
        eventPublisher.publishEvent(event);
    }
}
