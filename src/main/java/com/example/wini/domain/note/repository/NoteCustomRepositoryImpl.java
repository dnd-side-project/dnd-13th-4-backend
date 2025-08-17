package com.example.wini.domain.note.repository;

import static com.example.wini.domain.note.domain.QNote.note;

import com.example.wini.domain.note.domain.Note;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NoteCustomRepositoryImpl implements NoteCustomRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<Note> findSavedNotes() {
    return queryFactory.selectFrom(note).where(note.isSaved.eq(true)).fetch();
  }
}
