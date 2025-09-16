package com.example.wini.domain.note.service;

import static com.example.wini.global.common.constant.NoteConstants.DAILY_NOTE_LIMIT;
import static com.example.wini.global.error.exception.ErrorCode.*;

import com.example.wini.domain.common.util.MemberUtil;
import com.example.wini.domain.member.domain.Member;
import com.example.wini.domain.member.repository.MemberRepository;
import com.example.wini.domain.note.domain.Note;
import com.example.wini.domain.note.domain.SortOrder;
import com.example.wini.domain.note.dto.request.NoteCreateRequest;
import com.example.wini.domain.note.dto.response.NoteResponse;
import com.example.wini.domain.note.dto.response.SimpleNoteResponse;
import com.example.wini.domain.note.repository.NoteRepository;
import com.example.wini.domain.notification.domain.NotificationType;
import com.example.wini.domain.notification.event.NotificationEvent;
import com.example.wini.domain.room.entity.Room;
import com.example.wini.domain.room.repository.RoomRepository;
import com.example.wini.domain.template.domain.*;
import com.example.wini.domain.template.repository.action.ActionRepository;
import com.example.wini.domain.template.repository.closing.ClosingRepository;
import com.example.wini.domain.template.repository.emotion.EmotionRepository;
import com.example.wini.domain.template.repository.promise.PromiseRepository;
import com.example.wini.domain.template.repository.situation.SituationRepository;
import com.example.wini.global.error.exception.CustomException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final EmotionRepository emotionRepository;
    private final ActionRepository actionRepository;
    private final SituationRepository situationRepository;
    private final PromiseRepository promiseRepository;
    private final ClosingRepository closingRepository;
    private final MemberRepository memberRepository;
    private final RoomRepository roomRepository;
    private final MemberUtil memberUtil;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional(readOnly = false)
    public NoteResponse findNoteById(Long noteId) {
        Note note = noteRepository.findFullNote(noteId).orElseThrow(() -> new CustomException(NOTE_NOT_FOUND));
        Member me = memberUtil.getCurrentMember();
        if (note.getReceiver().equals(me)) {
            note.markAsRead();
        }
        return NoteResponse.from(note);
    }

    @Transactional(readOnly = true)
    public List<SimpleNoteResponse> findLatestNotesSorted() {
        Member me = memberUtil.getCurrentMember();
        Room room = roomRepository
                .findOpenRoomByMemberId(me.getId())
                .orElseThrow(() -> new CustomException(ROOM_NOT_FOUND));
        List<Note> notes = noteRepository.findLatestNotesSortedByCreatedAtDesc(me.getId(), room.getId());
        return notes.stream().map(SimpleNoteResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public List<SimpleNoteResponse> findSavedNotesSorted(String sort) {
        Member me = memberUtil.getCurrentMember();
        Room room = roomRepository
                .findOpenRoomByMemberId(me.getId())
                .orElseThrow(() -> new CustomException(ROOM_NOT_FOUND));
        SortOrder sortOrder = SortOrder.from(sort);
        List<Note> notes = noteRepository.findSavedNotesSortedByCreatedAt(me.getId(), room.getId(), sortOrder);
        return notes.stream().map(SimpleNoteResponse::from).toList();
    }

    @Transactional(readOnly = false)
    public NoteResponse createNote(NoteCreateRequest request) {
        Member me = memberUtil.getCurrentMember();
        Member mate = memberRepository
                .findRoommateByMemberId(me.getId())
                .orElseThrow(() -> new CustomException(MATE_NOT_FOUND));

        Note note = buildNewNote(request, me, mate);
        noteRepository.save(note);
        notifyRoommateOfNewNote(mate.getId());
        return NoteResponse.from(note);
    }

    @Transactional(readOnly = false)
    public NoteResponse saveNote(Long noteId) {
        Note note = noteRepository.findById(noteId).orElseThrow(() -> new CustomException(NOTE_NOT_FOUND));
        validateNoteReceiver(note);
        note.markAsSaved();
        return NoteResponse.from(note);
    }

    private Note buildNewNote(NoteCreateRequest request, Member me, Member mate) {
        Room room = roomRepository
                .findOpenRoomByMemberId(me.getId())
                .orElseThrow(() -> new CustomException(ROOM_NOT_FOUND));
        Emotion emotion = emotionRepository
                .findById(request.emotionId())
                .orElseThrow(() -> new CustomException(EMOTION_NOT_FOUND));
        Action action =
                actionRepository.findById(request.actionId()).orElseThrow(() -> new CustomException(ACTION_NOT_FOUND));
        Situation situation = situationRepository
                .findById(request.situationId())
                .orElseThrow(() -> new CustomException(SITUATION_NOT_FOUND));
        Promise promise = promiseRepository
                .findById(request.promiseId())
                .orElseThrow(() -> new CustomException(PROMISE_NOT_FOUND));
        Closing closing = closingRepository
                .findById(request.closingId())
                .orElseThrow(() -> new CustomException(CLOSING_NOT_FOUND));
        int nextSequence = getNextSequence();

        checkDailyLimit(nextSequence);

        return Note.create(me, mate, room, emotion, action, situation, promise, closing, nextSequence);
    }

    private int getNextSequence() {
        Member me = memberUtil.getCurrentMember();
        Room room = roomRepository
                .findOpenRoomByMemberId(me.getId())
                .orElseThrow(() -> new CustomException(ROOM_NOT_FOUND));
        return noteRepository.countNotesSentToday(me.getId(), room.getId()).intValue() + 1;
    }

    private void notifyRoommateOfNewNote(Long mateMemberId) {
        NotificationEvent event = NotificationEvent.from(mateMemberId, NotificationType.NEW_NOTE);
        eventPublisher.publishEvent(event);
    }

    private void validateNoteReceiver(Note note) {
        Member me = memberUtil.getCurrentMember();
        if (!note.getReceiver().equals(me)) {
            throw new CustomException(NOTE_RECEIVER_MISMATCH);
        }
    }

    private void checkDailyLimit(int nextSequence) {
        if (nextSequence > DAILY_NOTE_LIMIT) {
            throw new CustomException(NOTE_LIMIT_EXCEEDED);
        }
    }
}
