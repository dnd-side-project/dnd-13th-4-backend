package com.example.wini.domain.template.service;

import com.example.wini.domain.template.domain.Closing;
import com.example.wini.domain.template.domain.EmotionType;
import com.example.wini.domain.template.dto.response.ClosingResponse;
import com.example.wini.domain.template.repository.ClosingRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClosingService {

  private final ClosingRepository closingRepository;

  @Transactional(readOnly = true)
  public List<ClosingResponse> findAllClosingsByEmotionType(String emotionType) {
    EmotionType type = EmotionType.from(emotionType);
    List<Closing> closings = closingRepository.findAllByEmotionType(type);
    return closings.stream().map(ClosingResponse::from).toList();
  }
}
