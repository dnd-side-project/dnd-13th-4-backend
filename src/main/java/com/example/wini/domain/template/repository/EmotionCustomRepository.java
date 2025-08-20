package com.example.wini.domain.template.repository;

import com.example.wini.domain.template.domain.Emotion;
import com.example.wini.domain.template.domain.EmotionType;
import java.util.List;

public interface EmotionCustomRepository {
  List<Emotion> findAllByEmotionType(EmotionType emotionType);
}
