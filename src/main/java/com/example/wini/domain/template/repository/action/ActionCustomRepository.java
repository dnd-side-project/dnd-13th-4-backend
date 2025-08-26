package com.example.wini.domain.template.repository.action;

import com.example.wini.domain.template.domain.Action;
import com.example.wini.domain.template.domain.ActionCategory;
import com.example.wini.domain.template.domain.EmotionType;
import java.util.List;
import java.util.Map;

public interface ActionCustomRepository {
    Map<ActionCategory, List<Action>> findActionsGroupedByCategoryByEmotionType(EmotionType emotionType);
}
