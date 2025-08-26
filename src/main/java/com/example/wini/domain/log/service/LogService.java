package com.example.wini.domain.log.service;

import static com.example.wini.global.error.exception.ErrorCode.*;

import com.example.wini.domain.log.dto.response.StatisticsResponse;
import com.example.wini.domain.note.repository.NoteRepository;
import com.example.wini.domain.room.entity.Room;
import com.example.wini.domain.room.repository.RoomRepository;
import com.example.wini.global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogService {

    private final NoteRepository noteRepository;
    private final RoomRepository roomRepository;

    @Transactional(readOnly = true)
    public StatisticsResponse getWeeklyStatistics() {
        // TODO : 인가받은 사용자의 노트로 필터링 필요
        Long notesSentThisWeek = noteRepository.countNotesSentThisWeekByMemberId(1L);
        Long notesReceivedThisWeek = noteRepository.countNotesReceivedThisWeekByMemberId(1L);
        Room room = roomRepository.findOpenRoomByMemberId(1L).orElseThrow(() -> new CustomException(ROOM_NOT_FOUND));

        return StatisticsResponse.of(notesSentThisWeek, notesReceivedThisWeek, room.getCreatedAt());
    }
}
