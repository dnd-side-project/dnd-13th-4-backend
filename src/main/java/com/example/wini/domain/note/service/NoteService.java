package com.example.wini.domain.note.service;

import static com.example.wini.global.error.exception.ErrorCode.*;

import com.example.wini.domain.note.domain.Note;
import com.example.wini.domain.note.dto.request.NoteCreateRequest;
import com.example.wini.domain.note.dto.response.NoteResponse;
import com.example.wini.domain.note.repository.NoteRepository;
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

  @Transactional(readOnly = true)
  public NoteResponse findNoteById(Long noteId) {
    Note note =
        noteRepository
            .findFullNoteById(noteId)
            .orElseThrow(() -> new CustomException(NOTE_NOT_FOUND));
    // TODO : 인가받은 사용자 확인 후 읽음 처리 필요
    return NoteResponse.from(note);
  }

  @Transactional(readOnly = true)
  public List<NoteResponse> findLatestNotes() {
    // TODO : 인가받은 사용자의 노트로 필터링 필요
    List<Note> notes = noteRepository.findLatestNotes();
    return notes.stream().map(NoteResponse::from).toList();
  }

  @Transactional(readOnly = true)
  public List<NoteResponse> findSavedNotes() {
    // TODO : 인가받은 사용자의 노트로 필터링 필요
    List<Note> notes = noteRepository.findSavedNotes();
    return notes.stream().map(NoteResponse::from).toList();
  }

  @Transactional(readOnly = false)
  public NoteResponse createNote(NoteCreateRequest request) {
    Note note = buildNewNote(request);
    noteRepository.save(note);
    return NoteResponse.from(note);
  }

  @Transactional(readOnly = false)
  public NoteResponse saveNote(Long noteId) {
    // TODO : 인가받은 사용자로 저장 가능한지 판단
    Note note =
        noteRepository.findById(noteId).orElseThrow(() -> new CustomException(NOTE_NOT_FOUND));
    note.markAsSaved();
    return NoteResponse.from(note);
  }

  private Note buildNewNote(NoteCreateRequest request) {
    // TODO : 인가받은 사용자로 송신자, 수신자 판단
    Emotion emotion =
        emotionRepository
            .findById(request.emotionId())
            .orElseThrow(() -> new CustomException(EMOTION_NOT_FOUND));
    Action action =
        actionRepository
            .findById(request.actionId())
            .orElseThrow(() -> new CustomException(ACTION_NOT_FOUND));
    Situation situation =
        situationRepository
            .findById(request.situationId())
            .orElseThrow(() -> new CustomException(SITUATION_NOT_FOUND));
    Promise promise =
        promiseRepository
            .findById(request.promiseId())
            .orElseThrow(() -> new CustomException(PROMISE_NOT_FOUND));
    Closing closing =
        closingRepository
            .findById(request.closingId())
            .orElseThrow(() -> new CustomException(CLOSING_NOT_FOUND));
    int nextSequence = getNextSequence();

    return Note.create(1L, 2L, emotion, action, situation, promise, closing, nextSequence);
  }

  private int getNextSequence() {
    // TODO : 인가받은 사용자의 노트로 필터링 필요
    return noteRepository.countTodayNotes().intValue() + 1;
  }
}
