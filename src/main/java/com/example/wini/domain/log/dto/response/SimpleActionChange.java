package com.example.wini.domain.log.dto.response;

public record SimpleActionChange(String text, int monthlyChange) {
    public static SimpleActionChange from(ActionChange actionChange) {
        return new SimpleActionChange(actionChange.action().getText(), actionChange.monthlyChange());
    }
}
