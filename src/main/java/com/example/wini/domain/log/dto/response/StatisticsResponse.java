package com.example.wini.domain.log.dto.response;

import java.time.LocalDateTime;

public record StatisticsResponse(
        long notesSentThisWeek, long notesReceivedThisWeek, long totalNotesExchanged, LocalDateTime roomJoinedAt) {
    public static StatisticsResponse of(
            Long notesSentThisWeek, Long notesReceivedThisWeek, Long totalNotesExchanged, LocalDateTime roomJoinedAt) {
        return new StatisticsResponse(notesSentThisWeek, notesReceivedThisWeek, totalNotesExchanged, roomJoinedAt);
    }
}
