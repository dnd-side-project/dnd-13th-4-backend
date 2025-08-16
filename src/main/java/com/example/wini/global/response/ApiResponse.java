package com.example.wini.global.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiResponse<T> {

  private final int status;
  private final T data;

  private ApiResponse(final int status, final T data) {
    this.status = status;
    this.data = data;
  }

  // 상태 코드 200 OK를 기본값으로 사용하는 성공 응답
  public static <T> ApiResponse<T> success(final T data) {
    return new ApiResponse<>(HttpStatus.OK.value(), data);
  }

  // 상태 코드 200 OK를 기본값으로 사용하는 성공 응답 (데이터 X)
  public static <T> ApiResponse<T> success() {
    return new ApiResponse<>(HttpStatus.OK.value(), null);
  }

  // 데이터를 포함한 성공 응답
  public static <T> ApiResponse<T> success(final int status, final T data) {
    return new ApiResponse<>(status, data);
  }

  // 데이터 없이 싱테 코드만 포함한 성공 응답
  public static <T> ApiResponse<T> success(final int status) {
    return new ApiResponse<>(status, null);
  }
}
