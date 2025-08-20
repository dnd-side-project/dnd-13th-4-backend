package com.example.wini.domain.template.service;

import com.example.wini.domain.template.domain.Action;
import com.example.wini.domain.template.dto.response.ActionResponse;
import com.example.wini.domain.template.repository.ActionRepository;
import java.util.List;
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
  public List<ActionResponse> findAllActions() {
    List<Action> actions = actionRepository.findAllWithCategory();
    return actions.stream().map(ActionResponse::from).toList();
  }
}
