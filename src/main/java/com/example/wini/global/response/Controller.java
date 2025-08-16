package com.example.wini.global.response;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

  @GetMapping("/")
  public ResponseEntity<ApiResponse<String>> example() {
    return ResponseEntity.ok(ApiResponse.success("예시"));
  }
}
