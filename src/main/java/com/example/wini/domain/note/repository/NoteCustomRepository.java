package com.example.wini.domain.note.repository;

import com.example.wini.domain.log.dto.response.ActionChange;
import com.example.wini.domain.log.dto.response.WeeklyNoteCount;
import com.example.wini.domain.note.domain.Note;
import com.example.wini.domain.template.domain.ActionCategory;
import com.example.wini.domain.template.domain.EmotionType;
import java.util.List;
import java.util.Optional;

public interface NoteCustomRepository {
    Optional<Note> findFullNote(Long noteId);

    List<Note> findLatestNotes(Long memberId, Long roomId);

    List<Note> findSavedNotes(Long memberId, Long roomId);

    ActionChange findMostIncreasedPositiveActionChange(Long memberId, Long roomId);

    ActionChange findMostDecreasedNegativeActionChange(Long memberId, Long roomId);

    ActionCategory findTopActionCategoryInLast30Days(Long memberId, Long roomId, EmotionType emotionType);

    List<WeeklyNoteCount> getWeeklyPositiveNoteCounts(Long memberId, Long roomId);

    Long countNotesSentToday(Long memberId, Long roomId);

    Long countNotesSentThisWeek(Long memberId, Long roomId);

    Long countNotesReceivedThisWeek(Long memberId, Long roomId);

    Long countTotalNotesExchanged(Long roomId);
}
