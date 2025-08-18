package com.example.wini.domain.member.dto.request;

import com.example.wini.domain.member.dto.common.ReservedTimeInfo;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record MemberStatusUpdateRequest(
    @NotNull Long statusId,
    @NotNull LocalDateTime startedAt,
    @NotNull ReservedTimeInfo reservedTimeInfo) {}
