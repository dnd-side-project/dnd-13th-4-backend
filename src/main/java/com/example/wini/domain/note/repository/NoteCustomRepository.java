package com.example.wini.domain.note.repository;

import com.example.wini.domain.note.domain.Note;
import java.util.List;
import java.util.Optional;

public interface NoteCustomRepository {
  Optional<Note> findWithEmotionAndActionAndSituationAndPromiseByNoteId(Long noteId);

  List<Note> findLatestNotes();

  List<Note> findSavedNotes();

  Long countTodayNotes();
}
