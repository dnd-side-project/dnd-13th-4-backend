package com.example.wini.domain.log.dto.response;

public record GrowthResponse(SimpleActionChange increasedPositiveAction, SimpleActionChange decreasedNegativeAction) {
    public static GrowthResponse from(
            ActionChange increasedPositiveActionChange, ActionChange decreasedNegativeActionChange) {
        return new GrowthResponse(
                SimpleActionChange.from(increasedPositiveActionChange),
                SimpleActionChange.from(decreasedNegativeActionChange));
    }
}
