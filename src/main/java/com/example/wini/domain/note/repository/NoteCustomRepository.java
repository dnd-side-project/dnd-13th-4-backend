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

    List<Note> findLatestNotes(Long memberId);

    List<Note> findSavedNotes(Long memberId);

    ActionChange findMostIncreasedPositiveActionChange(Long memberId);

    ActionChange findMostDecreasedNegativeActionChange(Long memberId);

    ActionCategory findTopActionCategoryInLast30Days(Long memberId, EmotionType emotionType);

    List<WeeklyNoteCount> getWeeklyPositiveNoteCounts(Long memberId);

    Long countNotesSentToday(Long memberId);

    Long countNotesSentThisWeek(Long memberId);

    Long countNotesReceivedThisWeek(Long memberId);
}
