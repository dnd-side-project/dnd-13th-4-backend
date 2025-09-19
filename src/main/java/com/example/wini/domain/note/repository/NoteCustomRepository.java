package com.example.wini.domain.note.repository;

import com.example.wini.domain.log.dto.response.ActionChange;
import com.example.wini.domain.log.dto.response.EmotionCount;
import com.example.wini.domain.log.dto.response.WeeklyNoteCount;
import com.example.wini.domain.note.domain.Note;
import com.example.wini.domain.note.domain.SortOrder;
import com.example.wini.domain.template.domain.ActionCategory;
import com.example.wini.domain.template.domain.EmotionType;
import java.util.List;
import java.util.Optional;

public interface NoteCustomRepository {
    Optional<Note> findFullNote(Long noteId);

    List<Note> findLatestNotesSortedByCreatedAtDesc(Long memberId, Long roomId);

    List<Note> findSavedNotesSortedByCreatedAt(Long memberId, Long roomId, SortOrder sortOrder);

    ActionChange findMostIncreasedPositiveActionChange(Long memberId, Long roomId);

    ActionChange findMostDecreasedNegativeActionChange(Long memberId, Long roomId);

    ActionCategory findTopActionCategoryInLast30Days(Long memberId, Long roomId, EmotionType emotionType);

    List<WeeklyNoteCount> getWeeklyPositiveNoteCounts(Long memberId, Long roomId);

    List<EmotionCount> countThisWeekNotesByEmotion(Long memberId, Long roomId);

    Long countNotesSentToday(Long memberId, Long roomId);

    Long countNotesSentThisWeek(Long memberId, Long roomId);

    Long countNotesReceivedThisWeek(Long memberId, Long roomId);

    Long countTotalNotesExchanged(Long roomId);
}
