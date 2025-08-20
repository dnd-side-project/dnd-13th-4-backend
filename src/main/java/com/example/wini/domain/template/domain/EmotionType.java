package com.example.wini.domain.template.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EmotionType {
  POSITIVE("긍정"),
  NEGATIVE("부정"),
  ;

  private final String value;
}
