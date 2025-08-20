package com.example.wini.domain.template.repository.action;

import com.example.wini.domain.template.domain.ActionCategory;
import com.example.wini.domain.template.domain.EmotionType;
import java.util.List;

public interface ActionCategoryCustomRepository {
  List<ActionCategory> findAllWithActionsByEmotionType(EmotionType emotionType);
}
