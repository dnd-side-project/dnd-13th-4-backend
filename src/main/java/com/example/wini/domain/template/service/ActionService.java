package com.example.wini.domain.template.service;

import com.example.wini.domain.template.domain.Action;
import com.example.wini.domain.template.domain.ActionCategory;
import com.example.wini.domain.template.domain.EmotionType;
import com.example.wini.domain.template.dto.response.ActionCategoryResponse;
import com.example.wini.domain.template.repository.action.ActionRepository;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActionService {

    private final ActionRepository actionRepository;

    @Transactional(readOnly = true)
    public List<ActionCategoryResponse> findAllActionsGroupedByCategoryByEmotionType(String emotionType) {
        EmotionType type = EmotionType.from(emotionType);
        Map<ActionCategory, List<Action>> actionsMap = actionRepository.findActionsGroupedByCategoryByEmotionType(type);
        return actionsMap.entrySet().stream()
                .sorted(Comparator.comparing(entry -> entry.getKey().getId()))
                .map(entry -> ActionCategoryResponse.from(entry.getKey(), entry.getValue()))
                .toList();
    }
}
