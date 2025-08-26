package com.example.wini.domain.log.dto.response;

public record ActionTextWithCount(String actionText, long count) {
    public static ActionTextWithCount from(ActionAndCount actionAndCount) {
        return new ActionTextWithCount(actionAndCount.action().getText(), actionAndCount.count());
    }
}
