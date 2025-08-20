package com.example.wini.domain.note.domain;

import com.example.wini.domain.common.BaseEntity;
import com.example.wini.domain.template.domain.*;
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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "promise_id")
  private Promise promise;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "closing_id")
  private Closing closing;

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
      Promise promise,
      Closing closing,
      int sequence) {
    this.memberRoomSenderId = memberRoomSenderId;
    this.memberRoomReceiverId = memberRoomReceiverId;
    this.emotion = emotion;
    this.action = action;
    this.situation = situation;
    this.promise = promise;
    this.closing = closing;
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
      Promise promise,
      Closing closing,
      int sequence) {
    return Note.builder()
        .memberRoomSenderId(memberRoomSenderId)
        .memberRoomReceiverId(memberRoomReceiverId)
        .emotion(emotion)
        .action(action)
        .situation(situation)
        .promise(promise)
        .closing(closing)
        .sequence(sequence)
        .build();
  }

  public void markAsSaved() {
    isSaved = true;
  }
}
