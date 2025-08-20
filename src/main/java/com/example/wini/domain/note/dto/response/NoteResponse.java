package com.example.wini.domain.note.dto.response;

import com.example.wini.domain.note.domain.Note;
import java.time.LocalDateTime;

public record NoteResponse(
    Long id,
    Long memberRoomSenderId,
    Long memberRoomReceiverId,
    Long emotionId,
    Long situationId,
    Long actionId,
    Long promiseId,
    Long closingId,
    int sequence,
    boolean isRead,
    boolean isSaved,
    LocalDateTime createdAt) {
  public static NoteResponse from(Note note) {
    return new NoteResponse(
        note.getId(),
        note.getMemberRoomSenderId(),
        note.getMemberRoomReceiverId(),
        note.getEmotion().getId(),
        note.getSituationId(),
        note.getAction().getId(),
        note.getPromiseId(),
        note.getClosingId(),
        note.getSequence(),
        note.isRead(),
        note.isSaved(),
        note.getCreatedAt());
  }
}
