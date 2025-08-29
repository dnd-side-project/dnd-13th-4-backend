package com.example.wini.domain.note.dto.response;

import com.example.wini.domain.note.domain.Note;
import com.example.wini.domain.template.dto.response.*;
import java.time.LocalDateTime;

public record SimpleNoteResponse(Long id, EmotionResponse emotion, boolean isRead, LocalDateTime createdAt) {
    public static SimpleNoteResponse from(Note note) {
        return new SimpleNoteResponse(
                note.getId(), EmotionResponse.from(note.getEmotion()), note.isRead(), note.getCreatedAt());
    }
}
