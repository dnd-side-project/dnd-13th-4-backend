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

  // Authentication
  UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증되지 않은 사용자입니다."),
  PERMISSION_DENIED(HttpStatus.FORBIDDEN, "권한이 거부되었습니다."),

  // Member
  MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."),
  ;

  private final HttpStatus httpStatus;
  private final String message;
}
