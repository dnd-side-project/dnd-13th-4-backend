package com.example.wini.domain.log.dto.response;

public record WeeklyNoteCount(int weeksAgo, long count) {
    public static WeeklyNoteCount of(int weeksAgo, Long count) {
        return new WeeklyNoteCount(weeksAgo, count);
    }
}
