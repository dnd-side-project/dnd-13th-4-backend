package com.example.wini.domain.log.dto.response;

public record SimpleActionChange(String text, Long monthlyChange) {
    public static SimpleActionChange from(ActionChange actionChange) {
        return new SimpleActionChange(actionChange.action().getText(), actionChange.monthlyChange());
    }
}
