package com.example.wini.domain.note.service;

import static com.example.wini.global.error.exception.ErrorCode.*;

import com.example.wini.domain.note.domain.Note;
import com.example.wini.domain.note.dto.response.NoteResponse;
import com.example.wini.domain.note.repository.NoteRepository;
import com.example.wini.global.error.exception.CustomException;
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

    return NoteResponse.from(note);
  }
}
