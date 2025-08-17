package com.example.wini.domain.note.domain;

import com.example.wini.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Note extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Long memberRoomSenderId;

  @Column(nullable = false)
  private Long memberRoomReceiverId;

  @Column(nullable = false)
  private Long emotionId;

  @Column(nullable = true)
  private Long situationId;

  @Column(nullable = false)
  private Long actionId;

  @Column(nullable = false)
  private Long promiseId;

  @Column(nullable = false)
  private Long closingId;

  @Column(nullable = false)
  private int sequence;

  @Column(nullable = false)
  private boolean isRead;

  @Column(nullable = false)
  private boolean isSaved;

  @Builder(access = AccessLevel.PRIVATE)
  private Note(
      Long memberRoomSenderId,
      Long memberRoomReceiverId,
      Long emotionId,
      Long situationId,
      Long actionId,
      Long promiseId,
      Long closingId,
      int sequence) {
    this.memberRoomSenderId = memberRoomSenderId;
    this.memberRoomReceiverId = memberRoomReceiverId;
    this.emotionId = emotionId;
    this.situationId = situationId;
    this.actionId = actionId;
    this.promiseId = promiseId;
    this.closingId = closingId;
    this.sequence = sequence;
    this.isRead = false;
    this.isSaved = false;
  }

  public static Note create(
      Long memberRoomSenderId,
      Long memberRoomReceiverId,
      Long emotionId,
      Long situationId,
      Long actionId,
      Long promiseId,
      Long closingId,
      int sequence) {
    return Note.builder()
        .memberRoomSenderId(memberRoomSenderId)
        .memberRoomReceiverId(memberRoomReceiverId)
        .emotionId(emotionId)
        .situationId(situationId)
        .actionId(actionId)
        .promiseId(promiseId)
        .closingId(closingId)
        .sequence(sequence)
        .build();
  }
}
