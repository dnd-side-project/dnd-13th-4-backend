package com.example.wini.domain.note.dto.request;

import jakarta.validation.constraints.NotNull;

public record NoteCreateRequest(
    @NotNull Long emotionId,
    Long situationId,
    @NotNull Long actionId,
    @NotNull Long promiseId,
    @NotNull Long closingId) {}
