package com.example.wini.domain.log.dto.response;

import java.time.LocalDateTime;

public record LogSimpleResponse(long notesSentThisWeek, long notesReceivedThisWeek, LocalDateTime roomJoinedAt) {
    public static LogSimpleResponse of(long notesSentThisWeek, long notesReceivedThisWeek, LocalDateTime roomJoinedAt) {
        return new LogSimpleResponse(notesSentThisWeek, notesReceivedThisWeek, roomJoinedAt);
    }
}
