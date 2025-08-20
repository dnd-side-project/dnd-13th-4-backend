package com.example.wini.domain.template.dto.response;

import com.example.wini.domain.template.domain.Closing;

public record ClosingResponse(Long id, String emotionType, String text) {
  public static ClosingResponse from(Closing closing) {
    return new ClosingResponse(
        closing.getId(), closing.getEmotionType().getValue(), closing.getText());
  }
}
