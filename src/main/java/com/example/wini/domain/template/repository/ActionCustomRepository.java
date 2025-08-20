package com.example.wini.domain.template.repository;

import com.example.wini.domain.template.domain.Action;
import java.util.List;

public interface ActionCustomRepository {
  List<Action> findAllWithCategory();
}
