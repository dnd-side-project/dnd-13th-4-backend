package com.example.wini.domain.template.repository.closing;

import com.example.wini.domain.template.domain.Closing;
import com.example.wini.domain.template.domain.EmotionType;
import java.util.List;

public interface ClosingCustomRepository {
  List<Closing> findAllByEmotionType(EmotionType emotionType);
}
