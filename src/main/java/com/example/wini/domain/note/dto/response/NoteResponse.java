package com.example.wini.domain.note.dto.response;

import com.example.wini.domain.note.domain.Note;
import com.example.wini.domain.template.dto.response.*;
import java.time.LocalDateTime;

public record NoteResponse(
        Long id,
        Long senderId,
        Long receiverId,
        Long roomId,
        EmotionResponse emotion,
        ActionResponse action,
        SituationResponse situation,
        PromiseResponse promise,
        ClosingResponse closing,
        int sequence,
        boolean isRead,
        boolean isSaved,
        LocalDateTime createdAt) {
    public static NoteResponse from(Note note) {
        return new NoteResponse(
                note.getId(),
                note.getSender().getId(),
                note.getReceiver().getId(),
                note.getRoomId(),
                EmotionResponse.from(note.getEmotion()),
                ActionResponse.from(note.getAction()),
                SituationResponse.from(note.getSituation()),
                PromiseResponse.from(note.getPromise()),
                ClosingResponse.from(note.getClosing()),
                note.getSequence(),
                note.isRead(),
                note.isSaved(),
                note.getCreatedAt());
    }
}
