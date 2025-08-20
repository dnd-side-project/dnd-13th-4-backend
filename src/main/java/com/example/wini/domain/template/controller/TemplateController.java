package com.example.wini.domain.template.controller;

import com.example.wini.domain.template.dto.response.*;
import com.example.wini.domain.template.service.*;
import com.example.wini.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/templates")
@Tag(name = "3. 템플릿 관리", description = "템플릿 관련 API")
@RequiredArgsConstructor
public class TemplateController {

    private final EmotionService emotionService;
    private final ActionService actionService;
    private final SituationService situationService;
    private final PromiseService promiseService;
    private final ClosingService closingService;

    @GetMapping("/emotions")
    @Operation(summary = "감정 리스트 조회", description = "감정 유형을 쿼리로 받아 감정 목록을 반환합니다.")
    public ResponseEntity<ApiResponse<List<EmotionResponse>>> getEmotionsByEmotionType(
            @RequestParam(value = "emotionType") @NotBlank String emotionType) {
        List<EmotionResponse> responses = emotionService.findAllEmotionsByEmotionType(emotionType);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/actions")
    @Operation(summary = "행동 리스트 조회", description = "감정 유형을 쿼리로 받아 행동 목록을 카테고리로 분류하여 반환합니다.")
    public ResponseEntity<ApiResponse<List<ActionCategoryResponse>>> getActionsByEmotionType(
            @RequestParam(value = "emotionType") @NotBlank String emotionType) {
        List<ActionCategoryResponse> responses = actionService.findAllActionCategoriesByEmotionType(emotionType);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/situations")
    @Operation(summary = "상황 리스트 조회", description = "감정 유형을 쿼리로 받아 상황 목록을 반환합니다.")
    public ResponseEntity<ApiResponse<List<SituationResponse>>> getSituationsByEmotionType(
            @RequestParam(value = "emotionType") @NotBlank String emotionType) {
        List<SituationResponse> responses = situationService.findAllSituationsByEmotionType(emotionType);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/promises")
    @Operation(summary = "약속 리스트 조회", description = "감정 유형을 쿼리로 받아 약속 목록을 반환합니다.")
    public ResponseEntity<ApiResponse<List<PromiseResponse>>> getPromisesByEmotionType(
            @RequestParam(value = "emotionType") @NotBlank String emotionType) {
        List<PromiseResponse> responses = promiseService.findAllPromisesByEmotionType(emotionType);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/closings")
    @Operation(summary = "끝맺음 리스트 조회", description = "감정 유형을 쿼리로 받아 끝맺음 목록을 반환합니다.")
    public ResponseEntity<ApiResponse<List<ClosingResponse>>> getClosingsByEmotionType(
            @RequestParam(value = "emotionType") @NotBlank String emotionType) {
        List<ClosingResponse> responses = closingService.findAllClosingsByEmotionType(emotionType);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }
}
