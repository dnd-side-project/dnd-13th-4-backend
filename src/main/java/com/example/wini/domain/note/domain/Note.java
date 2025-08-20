package com.example.wini.domain.note.domain;

import com.example.wini.domain.common.BaseEntity;
import com.example.wini.domain.template.domain.Action;
import com.example.wini.domain.template.domain.Emotion;
import com.example.wini.domain.template.domain.Situation;
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

  // TODO : 인증인가 후 MemberRoom 연결
  @Column(nullable = false)
  private Long memberRoomSenderId;

  @Column(nullable = false)
  private Long memberRoomReceiverId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "emotion_id")
  private Emotion emotion;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "action_id")
  private Action action;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "situation_id")
  private Situation situation;

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
      Emotion emotion,
      Action action,
      Situation situation,
      Long promiseId,
      Long closingId,
      int sequence) {
    this.memberRoomSenderId = memberRoomSenderId;
    this.memberRoomReceiverId = memberRoomReceiverId;
    this.emotion = emotion;
    this.action = action;
    this.situation = situation;
    this.promiseId = promiseId;
    this.closingId = closingId;
    this.sequence = sequence;
    this.isRead = false;
    this.isSaved = false;
  }

  public static Note create(
      Long memberRoomSenderId,
      Long memberRoomReceiverId,
      Emotion emotion,
      Action action,
      Situation situation,
      Long promiseId,
      Long closingId,
      int sequence) {
    return Note.builder()
        .memberRoomSenderId(memberRoomSenderId)
        .memberRoomReceiverId(memberRoomReceiverId)
        .emotion(emotion)
        .action(action)
        .situation(situation)
        .promiseId(promiseId)
        .closingId(closingId)
        .sequence(sequence)
        .build();
  }

  public void markAsSaved() {
    isSaved = true;
  }
}
