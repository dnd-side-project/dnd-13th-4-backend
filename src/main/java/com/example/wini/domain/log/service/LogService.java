package com.example.wini.domain.log.service;

import static com.example.wini.global.error.exception.ErrorCode.*;

import com.example.wini.domain.common.util.MemberUtil;
import com.example.wini.domain.log.dto.response.*;
import com.example.wini.domain.member.domain.Member;
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
    private final MemberUtil memberUtil;

    @Transactional(readOnly = true)
    public StatisticsResponse getWeeklyStatistics() {
        Member me = memberUtil.getCurrentMember();
        Long notesSentThisWeek = noteRepository.countNotesSentThisWeek(me.getId());
        Long notesReceivedThisWeek = noteRepository.countNotesReceivedThisWeek(me.getId());
        Room room = roomRepository
                .findOpenRoomByMemberId(me.getId())
                .orElseThrow(() -> new CustomException(ROOM_NOT_FOUND));

        return StatisticsResponse.of(notesSentThisWeek, notesReceivedThisWeek, room.getCreatedAt());
    }

    @Transactional(readOnly = true)
    public KeywordResponse getTopActionCategoriesInLast30Days() {
        Member me = memberUtil.getCurrentMember();
        ActionCategory positive = noteRepository.findTopActionCategoryInLast30Days(me.getId(), EmotionType.POSITIVE);
        ActionCategory negative = noteRepository.findTopActionCategoryInLast30Days(me.getId(), EmotionType.NEGATIVE);

        return KeywordResponse.from(positive, negative);
    }

    @Transactional(readOnly = true)
    public GrowthResponse getActionTrendsAndWeeklyPositiveNoteCounts() {
        Member me = memberUtil.getCurrentMember();
        ActionChange increasedPositiveAction = noteRepository.findMostIncreasedPositiveActionChange(me.getId());
        ActionChange decreasedNegativeAction = noteRepository.findMostDecreasedNegativeActionChange(me.getId());
        List<WeeklyNoteCount> weeklyPositiveNoteCounts = noteRepository.getWeeklyPositiveNoteCounts(me.getId());

        return GrowthResponse.from(increasedPositiveAction, decreasedNegativeAction, weeklyPositiveNoteCounts);
    }
}
