package com.example.wini.domain.note.dto.response;

import com.example.wini.domain.note.domain.Note;
import com.example.wini.domain.template.dto.response.ActionResponse;
import com.example.wini.domain.template.dto.response.EmotionResponse;
import com.example.wini.domain.template.dto.response.SituationResponse;
import java.time.LocalDateTime;

public record NoteResponse(
    Long id,
    Long memberRoomSenderId,
    Long memberRoomReceiverId,
    EmotionResponse emotion,
    ActionResponse action,
    SituationResponse situation,
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
        EmotionResponse.from(note.getEmotion()),
        ActionResponse.from(note.getAction()),
        SituationResponse.from(note.getSituation()),
        note.getPromiseId(),
        note.getClosingId(),
        note.getSequence(),
        note.isRead(),
        note.isSaved(),
        note.getCreatedAt());
  }
}
