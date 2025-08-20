package com.example.wini.global.response;

import com.example.wini.global.error.ErrorResponse;

public record GlobalResponse(int status, Object data) {
    public static GlobalResponse ok(int status, Object data) {
        return new GlobalResponse(status, data);
    }

    public static GlobalResponse error(int status, ErrorResponse errorResponse) {
        return new GlobalResponse(status, errorResponse);
    }
}
