package com.example.wini.domain.template.service;

import com.example.wini.domain.template.domain.Emotion;
import com.example.wini.domain.template.dto.response.EmotionResponse;
import com.example.wini.domain.template.repository.emotion.EmotionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmotionService {

    private final EmotionRepository emotionRepository;

    @Transactional(readOnly = true)
    public List<EmotionResponse> findAllEmotions() {
        List<Emotion> emotions = emotionRepository.findAll();
        return emotions.stream().map(EmotionResponse::from).toList();
    }
}
