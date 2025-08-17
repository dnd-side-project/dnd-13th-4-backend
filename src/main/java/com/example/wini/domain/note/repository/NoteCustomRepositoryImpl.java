package com.example.wini.domain.note.repository;

import static com.example.wini.domain.note.domain.QNote.note;

import com.example.wini.domain.note.domain.Note;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NoteCustomRepositoryImpl implements NoteCustomRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<Note> findLatestNotes() {
    return queryFactory.selectFrom(note).where(isLatest()).fetch();
  }

  @Override
  public List<Note> findTodayNotes() {
    return queryFactory.selectFrom(note).where(isToday()).fetch();
  }

  @Override
  public List<Note> findSavedNotes() {
    return queryFactory.selectFrom(note).where(note.isSaved.eq(true)).fetch();
  }

  private BooleanExpression isLatest() {
    return note.createdAt.after(LocalDateTime.now().minusHours(24));
  }

  private BooleanExpression isToday() {
    return note.createdAt.between(
        LocalDate.now().atStartOfDay(), LocalDate.now().plusDays(1).atStartOfDay());
  }
}
