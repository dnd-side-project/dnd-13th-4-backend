package com.example.wini.domain.log.dto.response;

public record SimpleActionChange(String text, long change) {
    public static SimpleActionChange from(ActionChange actionChange) {
        return new SimpleActionChange(actionChange.getAction().getText(), actionChange.getChange());
    }
}
