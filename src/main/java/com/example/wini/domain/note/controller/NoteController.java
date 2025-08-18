package com.example.wini.domain.note.controller;

import com.example.wini.domain.note.dto.request.NoteCreateRequest;
import com.example.wini.domain.note.dto.response.NoteResponse;
import com.example.wini.domain.note.service.NoteService;
import com.example.wini.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notes")
@Tag(name = "2. 마음쪽지 관리", description = "마음쪽지 관련 API")
@RequiredArgsConstructor
public class NoteController {

  private final NoteService noteService;

  @GetMapping("/{noteId}")
  @Operation(summary = "단일 쪽지 조회", description = "쪽지 내용을 반환합니다.")
  public ResponseEntity<ApiResponse<NoteResponse>> getNote(@PathVariable Long noteId) {
    NoteResponse response = noteService.findNoteById(noteId);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

  @GetMapping("/latest")
  @Operation(summary = "최근 받은 쪽지 리스트 조회", description = "24시간 내 받은 쪽지 목록을 반환합니다.")
  public ResponseEntity<ApiResponse<List<NoteResponse>>> getLatestNotes() {
    List<NoteResponse> responses = noteService.findLatestNotes();
    return ResponseEntity.ok(ApiResponse.success(responses));
  }

  @GetMapping("/saved")
  @Operation(summary = "보관된 쪽지 리스트 조회", description = "사용자가 저장한 쪽지 목록을 반환합니다.")
  public ResponseEntity<ApiResponse<List<NoteResponse>>> getSavedNotes() {
    List<NoteResponse> responses = noteService.findSavedNotes();
    return ResponseEntity.ok(ApiResponse.success(responses));
  }

  @PostMapping
  @Operation(summary = "쪽지 생성", description = "사용자가 쪽지를 생성합니다.")
  public ResponseEntity<ApiResponse<NoteResponse>> createNote(
      @Valid @ModelAttribute NoteCreateRequest request) {
    NoteResponse response = noteService.createNote(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
  }

  @PatchMapping("/{noteId}/save")
  @Operation(summary = "쪽지 저장", description = "사용자가 쪽지를 저장합니다.")
  public ResponseEntity<ApiResponse<NoteResponse>> saveNote(@PathVariable Long noteId) {
    NoteResponse response = noteService.saveNote(noteId);
    return ResponseEntity.ok(ApiResponse.success(response));
  }
}
