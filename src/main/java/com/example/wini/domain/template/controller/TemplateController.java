package com.example.wini.domain.template.controller;

import com.example.wini.domain.template.dto.response.EmotionResponse;
import com.example.wini.domain.template.service.EmotionService;
import com.example.wini.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@Tag(name = "3. 템플릿 관리", description = "템플릿 관련 API")
@RequiredArgsConstructor
public class TemplateController {

  private final EmotionService emotionService;

  @GetMapping("/emotions")
  @Operation(summary = "감정 리스트 조회", description = "감정 목록을 반환합니다.")
  public ResponseEntity<ApiResponse<List<EmotionResponse>>> getEmotions() {
    List<EmotionResponse> responses = emotionService.findAllEmotions();
    return ResponseEntity.ok(ApiResponse.success(responses));
  }
}
