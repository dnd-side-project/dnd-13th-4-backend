package com.example.wini.domain.note.repository;

import static com.example.wini.domain.note.domain.QNote.note;
import static com.example.wini.domain.template.domain.QAction.action;
import static com.example.wini.domain.template.domain.QActionCategory.actionCategory;
import static com.example.wini.domain.template.domain.QEmotion.emotion;

import com.example.wini.domain.log.dto.response.ActionChange;
import com.example.wini.domain.log.dto.response.EmotionCount;
import com.example.wini.domain.log.dto.response.WeeklyNoteCount;
import com.example.wini.domain.note.domain.Note;
import com.example.wini.domain.note.domain.SortOrder;
import com.example.wini.domain.template.domain.ActionCategory;
import com.example.wini.domain.template.domain.EmotionType;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;
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
    public List<Note> findLatestNotesSortedByCreatedAtDesc(Long memberId, Long roomId) {
        return queryFactory
                .selectFrom(note)
                .where(isThisRoom(roomId).and(isReceiver(memberId)).and(isCreatedLatest()))
                .orderBy(note.createdAt.desc())
                .fetch();
    }

    @Override
    public List<Note> findSavedNotesSortedByCreatedAt(Long memberId, Long roomId, SortOrder sortOrder) {
        Order order = sortOrder == SortOrder.ASC ? Order.ASC : Order.DESC;
        OrderSpecifier<?> orderSpecifier = new OrderSpecifier<>(order, note.createdAt);

        return queryFactory
                .selectFrom(note)
                .where(isThisRoom(roomId).and(isReceiver(memberId)).and(isSaved()))
                .orderBy(orderSpecifier)
                .fetch();
    }

    @Override
    public ActionChange findMostIncreasedPositiveActionChange(Long memberId, Long roomId) {
        LocalDate today = LocalDate.now();

        NumberExpression<Integer> rawChange = new CaseBuilder()
                .when(isCreatedThisMonth(today))
                .then(1)
                .when(isCreatedLastMonth(today))
                .then(-1)
                .otherwise(0);

        NumberExpression<Long> monthlyChange = rawChange.castToNum(Long.class);
        NumberExpression<Long> monthlyChangeSum = monthlyChange.sumLong().coalesce(0L);

        List<Tuple> results = queryFactory
                .select(action, monthlyChangeSum)
                .from(note)
                .join(note.action, action)
                .join(action.actionCategory, actionCategory)
                .where(isThisRoom(roomId)
                        .and(isReceiver(memberId))
                        .and(actionCategory.emotionType.eq(EmotionType.POSITIVE)))
                .groupBy(action.id)
                .fetch();

        return results.stream()
                .map(t -> new ActionChange(t.get(action), t.get(monthlyChangeSum)))
                .max(Comparator.comparing(ActionChange::monthlyChange))
                .orElse(null);
    }

    @Override
    public ActionChange findMostDecreasedNegativeActionChange(Long memberId, Long roomId) {
        LocalDate today = LocalDate.now();

        NumberExpression<Integer> rawChange = new CaseBuilder()
                .when(isCreatedThisMonth(today))
                .then(1)
                .when(isCreatedLastMonth(today))
                .then(-1)
                .otherwise(0);

        NumberExpression<Long> monthlyChange = rawChange.castToNum(Long.class);
        NumberExpression<Long> monthlyChangeSum = monthlyChange.sumLong().coalesce(0L);

        List<Tuple> results = queryFactory
                .select(action, monthlyChangeSum)
                .from(note)
                .join(note.action, action)
                .join(action.actionCategory, actionCategory)
                .where(isThisRoom(roomId)
                        .and(isReceiver(memberId))
                        .and(actionCategory.emotionType.eq(EmotionType.NEGATIVE)))
                .groupBy(action.id)
                .fetch();

        return results.stream()
                .map(t -> new ActionChange(t.get(action), t.get(monthlyChangeSum)))
                .min(Comparator.comparing(ActionChange::monthlyChange))
                .orElse(null);
    }

    @Override
    public ActionCategory findTopActionCategoryInLast30Days(Long memberId, Long roomId, EmotionType emotionType) {
        return queryFactory
                .select(actionCategory)
                .from(note)
                .join(note.action, action)
                .join(action.actionCategory, actionCategory)
                .where(isThisRoom(roomId)
                        .and(isReceiver(memberId))
                        .and(isCreatedInLast30Days())
                        .and(actionCategory.emotionType.eq(emotionType)))
                .groupBy(actionCategory)
                .orderBy(actionCategory.id.count().desc(), note.createdAt.max().desc())
                .fetchFirst();
    }

    @Override
    public List<WeeklyNoteCount> getWeeklyPositiveNoteCounts(Long memberId, Long roomId) {
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
                    .where(isThisRoom(roomId)
                            .and(isReceiver(memberId))
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
    public List<EmotionCount> countThisWeekNotesByEmotion(Long memberId, Long roomId) {
        List<Tuple> results = queryFactory
                .select(note.emotion, note.count())
                .from(note)
                .join(note.emotion, emotion)
                .where(isThisRoom(roomId)
                        .and(isSender(memberId).or(isReceiver(memberId)))
                        .and(isCreatedThisWeek()))
                .groupBy(note.emotion)
                .fetch();

        return results.stream()
                .map(t -> new EmotionCount(t.get(note.emotion), t.get(note.count())))
                .collect(Collectors.toList());
    }

    @Override
    public Long countNotesSentToday(Long memberId, Long roomId) {
        return queryFactory
                .select(note.count())
                .from(note)
                .where(isThisRoom(roomId).and(isSender(memberId)).and(isCreatedToday()))
                .fetchFirst();
    }

    @Override
    public Long countNotesSentThisWeek(Long memberId, Long roomId) {
        return queryFactory
                .select(note.count())
                .from(note)
                .where(isThisRoom(roomId).and(isSender(memberId)).and(isCreatedThisWeek()))
                .fetchFirst();
    }

    @Override
    public Long countNotesReceivedThisWeek(Long memberId, Long roomId) {
        return queryFactory
                .select(note.count())
                .from(note)
                .where(isThisRoom(roomId).and(isReceiver(memberId)).and(isCreatedThisWeek()))
                .fetchFirst();
    }

    @Override
    public Long countTotalNotesExchanged(Long roomId) {
        return queryFactory
                .select(note.count())
                .from(note)
                .where(isThisRoom(roomId))
                .fetchFirst();
    }

    private BooleanExpression isCreatedThisMonth(LocalDate today) {
        LocalDate firstDayOfThisMonth = today.withDayOfMonth(1);

        LocalDateTime startOfThisMonth = firstDayOfThisMonth.atStartOfDay();
        LocalDateTime endOfThisMonth = firstDayOfThisMonth.plusMonths(1).atStartOfDay();

        return note.createdAt.goe(startOfThisMonth).and(note.createdAt.lt(endOfThisMonth));
    }

    private BooleanExpression isCreatedLastMonth(LocalDate today) {
        LocalDate firstDayOfThisMonth = today.withDayOfMonth(1);
        LocalDate firstDayOfLastMonth = firstDayOfThisMonth.minusMonths(1);

        LocalDateTime startOfLastMonth = firstDayOfLastMonth.atStartOfDay();
        LocalDateTime endOfLastMonth = firstDayOfThisMonth.atStartOfDay();

        return note.createdAt.goe(startOfLastMonth).and(note.createdAt.lt(endOfLastMonth));
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

    private BooleanExpression isThisRoom(Long roomId) {
        return note.room.id.eq(roomId);
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
