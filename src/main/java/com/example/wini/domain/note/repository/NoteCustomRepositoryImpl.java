package com.example.wini.domain.note.repository;

import static com.example.wini.domain.note.domain.QNote.note;
import static com.example.wini.domain.template.domain.QAction.action;
import static com.example.wini.domain.template.domain.QActionCategory.actionCategory;

import com.example.wini.domain.log.dto.response.ActionChange;
import com.example.wini.domain.log.dto.response.WeeklyNoteCount;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NoteCustomRepositoryImpl implements NoteCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Note> findFullNote(Long noteId) {
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
    public List<Note> findLatestNotes(Long memberId) {
        return queryFactory
                .selectFrom(note)
                .where(isReceiver(memberId).and(isCreatedLatest()))
                .fetch();
    }

    @Override
    public List<Note> findSavedNotes(Long memberId) {
        return queryFactory
                .selectFrom(note)
                .where(isReceiver(memberId).and(isSaved()))
                .fetch();
    }

    @Override
    public ActionChange findMostIncreasedPositiveActionChange(Long memberId) {
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
    public ActionChange findMostDecreasedNegativeActionChange(Long memberId) {
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
    public ActionCategory findTopActionCategoryInLast30Days(Long memberId, EmotionType emotionType) {
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
    public List<WeeklyNoteCount> getWeeklyPositiveNoteCounts(Long memberId) {
        List<WeeklyNoteCount> results = new ArrayList<>();

        LocalDate today = LocalDate.now();
        LocalDateTime endDateTime = today.plusDays(1).atStartOfDay();

        int weeksAgo = 0;
        while (weeksAgo <= 8) {
            Long weeklyCount = queryFactory
                    .select(note.count())
                    .from(note)
                    .join(note.action, action)
                    .join(action.actionCategory, actionCategory)
                    .where(isReceiver(memberId)
                            .and(actionCategory.emotionType.eq(EmotionType.POSITIVE))
                            .and(isCreatedInLast7Days(endDateTime)))
                    .fetchOne();

            results.add(WeeklyNoteCount.of(weeksAgo, weeklyCount));
            endDateTime = endDateTime.minusDays(1);
            weeksAgo++;
        }

        return results;
    }

    @Override
    public Long countNotesSentToday(Long memberId) {
        return queryFactory
                .select(note.count())
                .from(note)
                .where(isSender(memberId).and(isCreatedToday()))
                .fetchFirst();
    }

    @Override
    public Long countNotesSentThisWeek(Long memberId) {
        return queryFactory
                .select(note.count())
                .from(note)
                .where(isSender(memberId).and(isCreatedThisWeek()))
                .fetchFirst();
    }

    @Override
    public Long countNotesReceivedThisWeek(Long memberId) {
        return queryFactory
                .select(note.count())
                .from(note)
                .where(isReceiver(memberId).and(isCreatedThisWeek()))
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

    private BooleanExpression isCreatedLatest() {
        return note.createdAt.after(LocalDateTime.now().minusHours(24));
    }

    private BooleanExpression isCreatedToday() {
        LocalDate today = LocalDate.now();

        LocalDateTime startOfToday = today.atStartOfDay();
        LocalDateTime startOfTomorrow = today.plusDays(1).atStartOfDay();

        return note.createdAt.goe(startOfToday).and(note.createdAt.lt(startOfTomorrow));
    }

    private BooleanExpression isCreatedThisWeek() {
        LocalDate today = LocalDate.now();

        LocalDateTime startOfWeek =
                today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).atStartOfDay();

        LocalDateTime startOfNextWeek =
                today.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atStartOfDay();

        return note.createdAt.goe(startOfWeek).and(note.createdAt.lt(startOfNextWeek));
    }

    private BooleanExpression isCreatedInLast7Days(LocalDateTime endDateTime) {
        LocalDateTime startDateTime = endDateTime.minusWeeks(1);
        return note.createdAt.goe(startDateTime).and(note.createdAt.lt(endDateTime));
    }

    private BooleanExpression isCreatedInLast30Days() {
        return note.createdAt.goe(LocalDateTime.now().minusDays(30));
    }

    private BooleanExpression isSender(Long memberId) {
        return note.sender.id.eq(memberId);
    }

    private BooleanExpression isReceiver(Long memberId) {
        return note.receiver.id.eq(memberId);
    }

    private BooleanExpression isSaved() {
        return note.isSaved.eq(true);
    }
}
