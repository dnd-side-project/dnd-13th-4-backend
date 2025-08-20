package com.example.wini.domain.template.repository;

import com.example.wini.domain.template.domain.ActionCategory;
import java.util.List;

public interface ActionCategoryCustomRepository {
  List<ActionCategory> findAllWithActions();
}
