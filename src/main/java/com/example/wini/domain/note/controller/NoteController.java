package com.example.wini.domain.note.controller;

import com.example.wini.domain.note.dto.request.NoteCreateRequest;
import com.example.wini.domain.note.dto.response.NoteResponse;
import com.example.wini.domain.note.dto.response.SimpleNoteResponse;
import com.example.wini.domain.note.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notes")
@Tag(name = "2. 마음쪽지 관리", description = "마음쪽지 관련 API")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @GetMapping("/{noteId}")
    @Operation(summary = "단일 쪽지 조회", description = "쪽지 내용을 반환합니다.")
    public NoteResponse getNote(@PathVariable Long noteId) {
        return noteService.findNoteById(noteId);
    }

    @GetMapping("/latest")
    @Operation(summary = "최근 받은 쪽지 리스트 조회", description = "24시간 내 받은 쪽지 목록을 최신순 정렬하여 반환합니다.")
    public List<SimpleNoteResponse> getLatestNotes() {
        return noteService.findLatestNotesSorted();
    }

    @GetMapping("/saved")
    @Operation(summary = "보관된 쪽지 리스트 조회", description = "사용자가 저장한 쪽지 목록을 생성일시를 기준으로 정렬하여 반환합니다.")
    public List<SimpleNoteResponse> getSavedNotes(
            @RequestParam(value = "sort", required = false, defaultValue = "latest") String sort) {
        // TODO : 페이징 추가 시 Pageable로 수정
        return noteService.findSavedNotesSorted(sort);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "쪽지 생성", description = "사용자가 쪽지를 생성합니다.")
    public NoteResponse createNote(@Valid @RequestBody NoteCreateRequest request) {
        return noteService.createNote(request);
    }

    @PatchMapping("/{noteId}/save")
    @Operation(summary = "쪽지 저장", description = "사용자가 쪽지를 저장합니다.")
    public NoteResponse saveNote(@PathVariable Long noteId) {
        return noteService.saveNote(noteId);
    }
}
