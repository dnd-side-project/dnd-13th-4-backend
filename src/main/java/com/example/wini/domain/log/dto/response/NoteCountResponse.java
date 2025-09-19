package com.example.wini.domain.log.dto.response;

public record NoteCountResponse(int count) {
    public static NoteCountResponse from(int count) {
        return new NoteCountResponse(count);
    }
}
