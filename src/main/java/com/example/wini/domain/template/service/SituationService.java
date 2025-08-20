package com.example.wini.domain.template.service;

import com.example.wini.domain.template.domain.EmotionType;
import com.example.wini.domain.template.domain.Situation;
import com.example.wini.domain.template.dto.response.SituationResponse;
import com.example.wini.domain.template.repository.SituationRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SituationService {

  private final SituationRepository situationRepository;

  @Transactional(readOnly = true)
  public List<SituationResponse> findAllSituationsByEmotionType(String emotionType) {
    EmotionType type = EmotionType.from(emotionType);
    List<Situation> situations = situationRepository.findAllByEmotionType(type);
    return situations.stream().map(SituationResponse::from).toList();
  }
}
