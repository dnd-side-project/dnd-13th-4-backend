package com.example.wini.domain.note.repository;

import static com.example.wini.domain.note.domain.QNote.note;

import com.example.wini.domain.note.domain.Note;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NoteCustomRepositoryImpl implements NoteCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Note> findFullNoteById(Long noteId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(note)
                .join(note.emotion)
                .fetchJoin()
                .join(note.action)
                .fetchJoin()
                .join(note.situation)
                .fetchJoin()
                .join(note.promise)
                .fetchJoin()
                .join(note.closing)
                .fetchJoin()
                .where(note.id.eq(noteId))
                .fetchOne());
    }

    @Override
    public List<Note> findLatestNotes() {
        return queryFactory.selectFrom(note).where(isLatest()).fetch();
    }

    @Override
    public List<Note> findSavedNotes() {
        return queryFactory.selectFrom(note).where(note.isSaved.eq(true)).fetch();
    }

    @Override
    public Long countTodayNotes() {
        return queryFactory.select(note.count()).from(note).where(isToday()).fetchFirst();
    }

    @Override
    public Long countNotesSentThisWeekByMemberId(Long memberId) {
        return queryFactory
                .select(note.count())
                .from(note)
                .where(isThisWeek().and(isSender(memberId)))
                .fetchFirst();
    }

    @Override
    public Long countNotesReceivedThisWeekByMemberId(Long memberId) {
        return queryFactory
                .select(note.count())
                .from(note)
                .where(isThisWeek().and(isReceiver(memberId)))
                .fetchFirst();
    }

    private BooleanExpression isLatest() {
        return note.createdAt.after(LocalDateTime.now().minusHours(24));
    }

    private BooleanExpression isToday() {
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = LocalDate.now().plusDays(1).atStartOfDay();
        return note.createdAt.goe(start).and(note.createdAt.lt(end));
    }

    private BooleanExpression isThisWeek() {
        LocalDateTime startOfWeek = LocalDate.now()
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                .atStartOfDay();
        LocalDateTime startOfNextWeek =
                LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atStartOfDay();
        return note.createdAt.goe(startOfWeek).and(note.createdAt.lt(startOfNextWeek));
    }

    private BooleanExpression isSender(Long memberId) {
        return note.senderId.eq(memberId);
    }

    private BooleanExpression isReceiver(Long memberId) {
        return note.receiverId.eq(memberId);
    }
}
