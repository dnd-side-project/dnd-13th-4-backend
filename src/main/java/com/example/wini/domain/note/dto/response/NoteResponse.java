package com.example.wini.domain.note.dto.response;

import com.example.wini.domain.note.domain.Note;

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
    boolean isSaved) {
  public static NoteResponse from(Note note) {
    return new NoteResponse(
        note.getId(),
        note.getMemberRoomSenderId(),
        note.getMemberRoomReceiverId(),
        note.getEmotionId(),
        note.getSituationId(),
        note.getActionId(),
        note.getPromiseId(),
        note.getClosingId(),
        note.getSequence(),
        note.isRead(),
        note.isSaved());
  }
}
