package com.example.wini.domain.log.service;

import static com.example.wini.global.error.exception.ErrorCode.*;

import com.example.wini.domain.log.dto.response.*;
import com.example.wini.domain.note.repository.NoteRepository;
import com.example.wini.domain.room.entity.Room;
import com.example.wini.domain.room.repository.RoomRepository;
import com.example.wini.domain.template.domain.ActionCategory;
import com.example.wini.domain.template.domain.EmotionType;
import com.example.wini.global.error.exception.CustomException;
import java.util.List;
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
        Long notesSentThisWeek = noteRepository.countNotesSentThisWeek(1L);
        Long notesReceivedThisWeek = noteRepository.countNotesReceivedThisWeek(1L);
        Room room = roomRepository.findOpenRoomByMemberId(1L).orElseThrow(() -> new CustomException(ROOM_NOT_FOUND));

        return StatisticsResponse.of(notesSentThisWeek, notesReceivedThisWeek, room.getCreatedAt());
    }

    @Transactional(readOnly = true)
    public KeywordResponse getTopActionCategoriesInLast30Days() {
        // TODO : 인가받은 사용자의 노트로 필터링 필요
        ActionCategory positive = noteRepository.findTopActionCategoryInLast30Days(1L, EmotionType.POSITIVE);
        ActionCategory negative = noteRepository.findTopActionCategoryInLast30Days(1L, EmotionType.NEGATIVE);

        return KeywordResponse.from(positive, negative);
    }

    @Transactional(readOnly = true)
    public GrowthResponse getActionTrendsAndWeeklyPositiveNoteCounts() {
        // TODO : 인가받은 사용자의 노트로 필터링 필요
        ActionChange increasedPositiveAction = noteRepository.findMostIncreasedPositiveActionChange(1L);
        ActionChange decreasedNegativeAction = noteRepository.findMostDecreasedNegativeActionChange(1L);
        List<WeeklyNoteCount> weeklyPositiveNoteCounts = noteRepository.getWeeklyPositiveNoteCounts(1L);

        return GrowthResponse.from(increasedPositiveAction, decreasedNegativeAction, weeklyPositiveNoteCounts);
    }
}
