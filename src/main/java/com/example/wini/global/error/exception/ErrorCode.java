package com.example.wini.global.error.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // Common
    METHOD_ARGUMENT_INVALID(HttpStatus.BAD_REQUEST, "유효하지 않은 method 인자 입니다."),
    METHOD_NOT_SUPPORTED(HttpStatus.METHOD_NOT_ALLOWED, "지원하지 않는 HTTP method 입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에 문제가 발생했습니다. 관리자에게 문의해주세요."),
    HTTP_MESSAGE_NOT_READABLE(HttpStatus.BAD_REQUEST, "잘못된 요청 메시지 형식입니다."),
    QUERY_TYPE_MISMATCH(HttpStatus.BAD_REQUEST, "올바르지 않은 쿼리 타입 입니다."),
    QUERY_PARAM_INVALID(HttpStatus.BAD_REQUEST, "올바르지 않은 쿼리 파라미터 값입니다."),
    QUERY_PARAM_NOT_FOUND(HttpStatus.BAD_REQUEST, "쿼리 파라미터가 존재하지 않습니다."),
    FIREBASE_KEY_FILE_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "Firebase 키 파일을 찾을 수 없습니다."),
    FIREBASE_INITIALIZATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "Firebase 연동 중 오류가 발생했습니다."),

    // Authentication
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증되지 않은 사용자입니다."),
    PERMISSION_DENIED(HttpStatus.FORBIDDEN, "권한이 거부되었습니다."),
    TOKEN_REQUIRED(HttpStatus.BAD_REQUEST, "토큰이 필요합니다."),
    INVALID_TOKEN_SIGNATURE(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰 서명입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
    UNSUPPORTED_TOKEN(HttpStatus.BAD_REQUEST, "지원하지 않는 토큰 형식입니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 Refresh 토큰입니다."),
    MALFORMED_TOKEN(HttpStatus.BAD_REQUEST, "올바르지 않은 형식의 토큰입니다."),
    TOKEN_VERIFICATION_FAILED(HttpStatus.BAD_REQUEST, "토큰 검증에 실패했습니다."),
    BLACKLISTED_TOKEN(HttpStatus.UNAUTHORIZED, "로그아웃 처리된 토큰입니다."),
    KAKAO_TOKEN_ISSUANCE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "카카오 액세스 토큰 발급에 실패했습니다."),
    KAKAO_USERINFO_FETCH_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "카카오 사용자 정보 조회에 실패했습니다."),
    APPLE_PUBLIC_KEY_NOT_FOUND(HttpStatus.BAD_REQUEST, "일치하는 애플 공개키를 찾을 수 없습니다."),
    INVALID_ID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 ID 토큰입니다."),

    // Member
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."),
    MATE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 룸메이트입니다."),

    // Status
    STATUS_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 상태입니다."),

    // Room
    ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 방입니다."),
    ALREADY_JOIN_ROOM(HttpStatus.BAD_REQUEST, "이미 방에 속해있습니다."),
    ROOM_IS_FULL(HttpStatus.BAD_REQUEST, "방의 정원이 가득 찼습니다."),

    // Note
    NOTE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 마음쪽지입니다."),
    NOTE_RECEIVER_MISMATCH(HttpStatus.BAD_REQUEST, "마음쪽지의 수신자가 아닙니다."),
    SORT_ORDER_NOT_FOUND(HttpStatus.BAD_REQUEST, "잘못된 정렬 순서입니다."),
    NOTE_LIMIT_EXCEEDED(HttpStatus.BAD_REQUEST, "하루 쪽지 개수 제한을 초과하였습니다."),

    // Template
    EMOTION_TYPE_NOT_FOUND(HttpStatus.BAD_REQUEST, "잘못된 감정 분류입니다."),
    EMOTION_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 감정입니다."),
    ACTION_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 행동입니다."),
    SITUATION_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 상황입니다."),
    PROMISE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 약속입니다."),
    CLOSING_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 끝맺음입니다."),

    // SSE
    SSE_CONNECTION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SSE 연결 오류가 발생했습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
