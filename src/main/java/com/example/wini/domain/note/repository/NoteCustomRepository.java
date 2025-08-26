package com.example.wini.domain.note.repository;

import com.example.wini.domain.note.domain.Note;
import com.example.wini.domain.template.domain.ActionCategory;
import com.example.wini.domain.template.domain.EmotionType;
import java.util.List;
import java.util.Optional;

public interface NoteCustomRepository {
    Optional<Note> findFullNoteById(Long noteId);

    List<Note> findLatestNotes();

    List<Note> findSavedNotes();

    ActionCategory findTopActionCategoryInLast30DaysByMemberIdAndEmotionType(Long memberId, EmotionType emotionType);

    Long countTodayNotes();

    Long countNotesSentThisWeekByMemberId(Long memberId);

    Long countNotesReceivedThisWeekByMemberId(Long memberId);
}
