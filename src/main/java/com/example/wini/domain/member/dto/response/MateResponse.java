package com.example.wini.domain.member.dto.response;

import com.example.wini.domain.member.dto.query.MateInfoQuery;
import java.time.LocalDateTime;

public record MateResponse(Long id, String name, String image, LocalDateTime joinedAt) {
    public static MateResponse from(MateInfoQuery query) {
        return new MateResponse(query.id(), query.name(), query.image(), query.joindAt());
    }
}
