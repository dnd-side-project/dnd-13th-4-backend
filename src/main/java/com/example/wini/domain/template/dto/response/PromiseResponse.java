package com.example.wini.domain.template.dto.response;

import com.example.wini.domain.template.domain.Promise;

public record PromiseResponse(Long id, String emotionType, String text) {
  public static PromiseResponse from(Promise promise) {
    return new PromiseResponse(
        promise.getId(), promise.getEmotionType().getValue(), promise.getText());
  }
}
