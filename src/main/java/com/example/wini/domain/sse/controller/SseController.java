package com.example.wini.domain.sse.controller;

import com.example.wini.domain.sse.service.SseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/sse")
@Tag(name = "9. SSE", description = "SSE 관련 API")
@RequiredArgsConstructor
public class SseController {

    private final SseService sseService;

    @GetMapping(value = "/subscribe", produces = "text/event-stream")
    @Operation(summary = "SSE 연결 요청", description = "SSE 연결 요청을 보냅니다.")
    public SseEmitter subscribe(
            @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
        return sseService.subscribe(lastEventId);
    }
}
