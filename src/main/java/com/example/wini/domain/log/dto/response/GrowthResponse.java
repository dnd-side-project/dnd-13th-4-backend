package com.example.wini.domain.log.dto.response;

import java.util.List;

public record GrowthResponse(
        SimpleActionChange increasedPositiveAction,
        SimpleActionChange decreasedNegativeAction,
        List<WeeklyNoteCount> weeklyPositiveNoteCounts) {
    public static GrowthResponse from(
            ActionChange increasedPositiveActionChange,
            ActionChange decreasedNegativeActionChange,
            List<WeeklyNoteCount> weeklyPositiveNoteCounts) {
        return new GrowthResponse(
                SimpleActionChange.from(increasedPositiveActionChange),
                SimpleActionChange.from(decreasedNegativeActionChange),
                weeklyPositiveNoteCounts);
    }
}
