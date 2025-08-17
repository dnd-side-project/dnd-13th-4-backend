package com.example.wini.domain.note.controller;

import com.example.wini.domain.note.dto.response.NoteResponse;
import com.example.wini.domain.note.service.NoteService;
import com.example.wini.global.response.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {

  private final NoteService noteService;

  @GetMapping("/{noteId}")
  public ResponseEntity<ApiResponse<NoteResponse>> getNote(@PathVariable Long noteId) {
    NoteResponse response = noteService.findNoteById(noteId);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

  @GetMapping("/saved")
  public ResponseEntity<ApiResponse<List<NoteResponse>>> getSavedNotes() {
    List<NoteResponse> responses = noteService.findSavedNotes();
    return ResponseEntity.ok(ApiResponse.success(responses));
  }
}
