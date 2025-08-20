package com.example.wini.domain.member.controller;

import com.example.wini.domain.member.dto.response.StatusResponse;
import com.example.wini.domain.member.service.StatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "6. 상태 관리", description = "상태 관련 API")
@RequiredArgsConstructor
public class StatusController {

    private final StatusService statusService;

    @GetMapping("/status")
    @Operation(summary = "상태 리스트 조회", description = "상태 리스트를 반환합니다.")
    public List<StatusResponse> getStatuses() {
        return statusService.getStatuses();
    }
}
