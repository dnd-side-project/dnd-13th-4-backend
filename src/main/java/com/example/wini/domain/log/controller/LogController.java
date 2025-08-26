package com.example.wini.domain.log.controller;

import com.example.wini.domain.log.dto.response.GrowthResponse;
import com.example.wini.domain.log.dto.response.KeywordResponse;
import com.example.wini.domain.log.dto.response.StatisticsResponse;
import com.example.wini.domain.log.service.LogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/log")
@Tag(name = "4. 통계", description = "통계 관련 API")
@RequiredArgsConstructor
public class LogController {

    private final LogService logService;

    @GetMapping("/statistics")
    @Operation(summary = "주간 통계 조회", description = "주간 통계 결과를 반환합니다.")
    public StatisticsResponse getStatistics() {
        return logService.getWeeklyStatistics();
    }

    @GetMapping("/keywords")
    @Operation(summary = "나를 대표하는 키워드", description = "최근 30일간 가장 많이 받은 긍정과 부정 카테고리를 반환합니다.")
    public KeywordResponse getKeywords() {
        return logService.getTopActionCategoriesInLast30Days();
    }

    @GetMapping("/growth")
    @Operation(summary = "나의 성장", description = "가장 많이 변화한 긍정 및 부정 액션과 횟수를 반환합니다.")
    public GrowthResponse getGrowth() {
        return logService.getActionTrends();
    }
}
