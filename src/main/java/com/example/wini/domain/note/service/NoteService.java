package com.example.wini.domain.note.service;

import static com.example.wini.global.error.exception.ErrorCode.*;

import com.example.wini.domain.note.domain.Note;
import com.example.wini.domain.note.dto.request.NoteCreateRequest;
import com.example.wini.domain.note.dto.response.NoteResponse;
import com.example.wini.domain.note.repository.NoteRepository;
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

  @Transactional(readOnly = true)
  public NoteResponse findNoteById(Long noteId) {
    Note note =
        noteRepository.findById(noteId).orElseThrow(() -> new CustomException(NOTE_NOT_FOUND));
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
    // TODO : 인가받은 사용자로 송신자, 수신자 판단
    int nextSequence = getNextSequence();
    Note note =
        Note.create(
            1L,
            2L,
            request.emotionId(),
            request.situationId(),
            request.actionId(),
            request.promiseId(),
            request.closingId(),
            nextSequence);
    return NoteResponse.from(note);
  }

  private int getNextSequence() {
    // TODO : 인가받은 사용자의 노트로 필터링 필요
    return noteRepository.countTodayNotes().intValue() + 1;
  }
}
