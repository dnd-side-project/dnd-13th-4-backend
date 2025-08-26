package com.example.wini.domain.log.controller;

import com.example.wini.domain.log.dto.response.LogSimpleResponse;
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

    @GetMapping("/simple")
    @Operation(summary = "홈 화면 통계 조회", description = "홈 화면에 들어가는 통계 결과를 반환합니다.")
    public LogSimpleResponse getSimpleLog() {
        return logService.generateSimpleLog();
    }
}
