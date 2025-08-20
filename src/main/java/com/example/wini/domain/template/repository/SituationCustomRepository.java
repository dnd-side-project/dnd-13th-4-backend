package com.example.wini.domain.template.repository;

import com.example.wini.domain.template.domain.EmotionType;
import com.example.wini.domain.template.domain.Situation;
import java.util.List;

public interface SituationCustomRepository {
  List<Situation> findAllByEmotionType(EmotionType emotionType);
}
