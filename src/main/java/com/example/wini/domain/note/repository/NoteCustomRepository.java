package com.example.wini.domain.note.repository;

import com.example.wini.domain.note.domain.Note;
import java.util.List;

public interface NoteCustomRepository {
  List<Note> findSavedNotes();
}
