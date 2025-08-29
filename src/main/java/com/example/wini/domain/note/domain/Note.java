package com.example.wini.domain.note.domain;

import com.example.wini.domain.common.BaseEntity;
import com.example.wini.domain.member.domain.Member;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Member sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Member receiver;

    @Column(nullable = false)
    private Long roomId;

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
            Member sender,
            Member receiver,
            Long roomId,
            Emotion emotion,
            Action action,
            Situation situation,
            Promise promise,
            Closing closing,
            int sequence) {
        this.sender = sender;
        this.receiver = receiver;
        this.roomId = roomId;
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
            Member sender,
            Member receiver,
            Long roomId,
            Emotion emotion,
            Action action,
            Situation situation,
            Promise promise,
            Closing closing,
            int sequence) {
        return Note.builder()
                .sender(sender)
                .receiver(receiver)
                .roomId(roomId)
                .emotion(emotion)
                .action(action)
                .situation(situation)
                .promise(promise)
                .closing(closing)
                .sequence(sequence)
                .build();
    }

    public void markAsRead() {
        isSaved = true;
    }

    public void markAsSaved() {
        isSaved = true;
    }
}
