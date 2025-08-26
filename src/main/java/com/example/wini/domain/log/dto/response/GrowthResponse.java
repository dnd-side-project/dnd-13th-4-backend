package com.example.wini.domain.log.dto.response;

public record GrowthResponse(ActionTextWithCount increasedPositiveAction, ActionTextWithCount decreasedNegativeAction) {
    public static GrowthResponse from(
            ActionAndCount increasedPositiveActionAndCount, ActionAndCount decreasedNegativeActionAndCount) {
        return new GrowthResponse(
                ActionTextWithCount.from(increasedPositiveActionAndCount),
                ActionTextWithCount.from(decreasedNegativeActionAndCount));
    }
}
