package com.example.wini.domain.template.repository;

import com.example.wini.domain.template.domain.Action;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActionRepository extends JpaRepository<Action, Long>, ActionCustomRepository {}
