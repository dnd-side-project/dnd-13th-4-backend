package com.example.wini.domain.note.repository;

import static com.example.wini.domain.note.domain.QNote.note;
import static com.example.wini.domain.template.domain.QAction.action;
import static com.example.wini.domain.template.domain.QActionCategory.actionCategory;

import com.example.wini.domain.log.dto.response.ActionChange;
import com.example.wini.domain.note.domain.Note;
import com.example.wini.domain.template.domain.ActionCategory;
import com.example.wini.domain.template.domain.EmotionType;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
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
    public ActionChange findMostIncreasedPositiveActionChangeByMemberId(Long memberId) {
        LocalDate today = LocalDate.now();
        NumberExpression<Long> thisMonthNotes = countThisMonthNotes(today);
        NumberExpression<Long> lastMonthNotes = countLastMonthNotes(today);
        NumberExpression<Long> increaseCount = thisMonthNotes.subtract(lastMonthNotes);

        return queryFactory
                .select(Projections.constructor(ActionChange.class, action, increaseCount))
                .from(note)
                .join(note.action, action)
                .join(action.actionCategory, actionCategory)
                .where(isReceiver(memberId).and(actionCategory.emotionType.eq(EmotionType.POSITIVE)))
                .groupBy(action)
                .orderBy(increaseCount.desc())
                .fetchFirst();
    }

    @Override
    public ActionChange findMostDecreasedNegativeActionChangeByMemberId(Long memberId) {
        LocalDate today = LocalDate.now();
        NumberExpression<Long> thisMonthNotes = countThisMonthNotes(today);
        NumberExpression<Long> lastMonthNotes = countLastMonthNotes(today);
        NumberExpression<Long> decreaseCount = thisMonthNotes.subtract(lastMonthNotes);

        return queryFactory
                .select(Projections.constructor(ActionChange.class, action, decreaseCount))
                .from(note)
                .join(note.action, action)
                .join(action.actionCategory, actionCategory)
                .where(isReceiver(memberId).and(actionCategory.emotionType.eq(EmotionType.NEGATIVE)))
                .groupBy(action)
                .orderBy(decreaseCount.asc())
                .fetchFirst();
    }

    @Override
    public ActionCategory findTopActionCategoryInLast30DaysByMemberIdAndEmotionType(
            Long memberId, EmotionType emotionType) {
        return queryFactory
                .select(actionCategory)
                .from(note)
                .join(note.action, action)
                .join(action.actionCategory, actionCategory)
                .where(isReceiver(memberId)
                        .and(isCreatedInLast30Days())
                        .and(actionCategory.emotionType.eq(emotionType)))
                .groupBy(actionCategory)
                .orderBy(actionCategory.id.count().desc(), note.createdAt.max().desc())
                .fetchFirst();
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

    private NumberExpression<Long> countThisMonthNotes(LocalDate today) {
        LocalDateTime startOfThisMonth = today.withDayOfMonth(1).atStartOfDay();
        LocalDateTime endOfThisMonth = today.plusDays(1).atStartOfDay();

        return new CaseBuilder()
                .when(note.createdAt.goe(startOfThisMonth).and(note.createdAt.lt(endOfThisMonth)))
                .then(1L)
                .otherwise(0L)
                .sum();
    }

    private NumberExpression<Long> countLastMonthNotes(LocalDate today) {
        LocalDate lastMonthOfToday = today.minusMonths(1);

        LocalDateTime startOfLastMonth = lastMonthOfToday.withDayOfMonth(1).atStartOfDay();
        LocalDateTime endOfLastMonth = lastMonthOfToday.plusDays(1).atStartOfDay();
        if (today.getDayOfMonth() == today.lengthOfMonth()) {
            endOfLastMonth =
                    lastMonthOfToday.with(TemporalAdjusters.lastDayOfMonth()).atStartOfDay();
        }

        return new CaseBuilder()
                .when(note.createdAt.goe(startOfLastMonth).and(note.createdAt.lt(endOfLastMonth)))
                .then(1L)
                .otherwise(0L)
                .sum();
    }

    private BooleanExpression isLatest() {
        return note.createdAt.after(LocalDateTime.now().minusHours(24));
    }

    private BooleanExpression isToday() {
        LocalDate today = LocalDate.now();

        LocalDateTime startOfToday = today.atStartOfDay();
        LocalDateTime startOfTomorrow = today.plusDays(1).atStartOfDay();

        return note.createdAt.goe(startOfToday).and(note.createdAt.lt(startOfTomorrow));
    }

    private BooleanExpression isThisWeek() {
        LocalDate today = LocalDate.now();

        LocalDateTime startOfWeek =
                today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).atStartOfDay();

        LocalDateTime startOfNextWeek =
                today.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atStartOfDay();

        return note.createdAt.goe(startOfWeek).and(note.createdAt.lt(startOfNextWeek));
    }

    private BooleanExpression isCreatedInLast30Days() {
        return note.createdAt.goe(LocalDateTime.now().minusDays(30));
    }

    private BooleanExpression isSender(Long memberId) {
        return note.senderId.eq(memberId);
    }

    private BooleanExpression isReceiver(Long memberId) {
        return note.receiverId.eq(memberId);
    }
}
