package com.example.wini.domain.member.dto.response;

import com.example.wini.domain.member.domain.Location;
import com.example.wini.domain.member.domain.Status;

public record StatusResponse(Long id, String emoji, String text, String request, Location location) {
    public static StatusResponse from(Status status) {
        return new StatusResponse(
                status.getId(), status.getEmoji(), status.getText(), status.getRequest(), status.getLocation());
    }
}
