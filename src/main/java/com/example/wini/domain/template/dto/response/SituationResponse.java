package com.example.wini.domain.template.dto.response;

import com.example.wini.domain.template.domain.Situation;

public record SituationResponse(Long id, String emotionType, String text) {
  public static SituationResponse from(Situation situation) {
    return new SituationResponse(
        situation.getId(), situation.getEmotionType().getValue(), situation.getText());
  }
}
