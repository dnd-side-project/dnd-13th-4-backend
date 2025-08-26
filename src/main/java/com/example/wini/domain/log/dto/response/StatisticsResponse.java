package com.example.wini.domain.log.dto.response;

import java.time.LocalDateTime;

public record StatisticsResponse(long notesSentThisWeek, long notesReceivedThisWeek, LocalDateTime roomJoinedAt) {
    public static StatisticsResponse of(
            long notesSentThisWeek, long notesReceivedThisWeek, LocalDateTime roomJoinedAt) {
        return new StatisticsResponse(notesSentThisWeek, notesReceivedThisWeek, roomJoinedAt);
    }
}
