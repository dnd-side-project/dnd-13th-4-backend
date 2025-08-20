package com.example.wini.domain.template.domain;

import static com.example.wini.global.error.exception.ErrorCode.EMOTION_TYPE_NOT_FOUND;

import com.example.wini.global.error.exception.CustomException;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EmotionType {
  POSITIVE("positive"),
  NEGATIVE("negative"),
  ;

  private final String value;

  public static EmotionType from(String value) {
    return Arrays.stream(EmotionType.values())
        .filter(emotionType -> emotionType.value.equals(value))
        .findFirst()
        .orElseThrow(() -> new CustomException(EMOTION_TYPE_NOT_FOUND));
  }
}
