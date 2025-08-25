package com.example.wini.domain.notification.dto.request;

import jakarta.validation.constraints.NotBlank;

public record FirebaseTokenSaveRequest(@NotBlank String token) {}
