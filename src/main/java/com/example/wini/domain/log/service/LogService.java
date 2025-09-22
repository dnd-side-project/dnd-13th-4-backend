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
    public StatisticsResponse generateStatistics() {
        Member me = memberUtil.getCurrentMember();
        Room room = roomRepository
                .findOpenRoomByMemberId(me.getId())
                .orElseThrow(() -> new CustomException(ROOM_NOT_FOUND));
        Long notesSentThisWeek = noteRepository.countNotesSentThisWeek(me.getId(), room.getId());
        Long notesReceivedThisWeek = noteRepository.countNotesReceivedThisWeek(me.getId(), room.getId());
        Long totalNotesExchanged = noteRepository.countTotalNotesExchanged(room.getId());

        return StatisticsResponse.of(
                notesSentThisWeek, notesReceivedThisWeek, totalNotesExchanged, room.getCreatedAt());
    }

    @Transactional(readOnly = true)
    public KeywordResponse getTopActionCategoriesInLast30Days() {
        Member me = memberUtil.getCurrentMember();
        Room room = roomRepository
                .findOpenRoomByMemberId(me.getId())
                .orElseThrow(() -> new CustomException(ROOM_NOT_FOUND));
        ActionCategory positive =
                noteRepository.findTopActionCategoryInLast30Days(me.getId(), room.getId(), EmotionType.POSITIVE);
        ActionCategory negative =
                noteRepository.findTopActionCategoryInLast30Days(me.getId(), room.getId(), EmotionType.NEGATIVE);

        return KeywordResponse.from(positive, negative);
    }

    @Transactional(readOnly = true)
    public GrowthResponse getActionTrendsAndWeeklyPositiveNoteCounts() {
        Member me = memberUtil.getCurrentMember();
        Room room = roomRepository
                .findOpenRoomByMemberId(me.getId())
                .orElseThrow(() -> new CustomException(ROOM_NOT_FOUND));
        ActionChange increasedPositiveAction =
                noteRepository.findMostIncreasedPositiveActionChange(me.getId(), room.getId());
        ActionChange decreasedNegativeAction =
                noteRepository.findMostDecreasedNegativeActionChange(me.getId(), room.getId());
        List<WeeklyNoteCount> weeklyPositiveNoteCounts =
                noteRepository.getWeeklyPositiveNoteCounts(me.getId(), room.getId());

        return GrowthResponse.from(increasedPositiveAction, decreasedNegativeAction, weeklyPositiveNoteCounts);
    }

    @Transactional(readOnly = true)
    public List<EmotionCountResponse> countThisWeekEmotion() {
        Member me = memberUtil.getCurrentMember();
        Room room = roomRepository
                .findOpenRoomByMemberId(me.getId())
                .orElseThrow(() -> new CustomException(ROOM_NOT_FOUND));

        List<EmotionCount> thisWeekEmotionCount = noteRepository.countThisWeekNotesByEmotion(me.getId(), room.getId());

        return thisWeekEmotionCount.stream().map(EmotionCountResponse::from).toList();
    }
}
