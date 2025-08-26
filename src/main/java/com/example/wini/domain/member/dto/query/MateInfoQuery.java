package com.example.wini.domain.member.dto.query;

import java.time.LocalDateTime;

public record MateInfoQuery(Long id, String name, String image, LocalDateTime joindAt) {}
