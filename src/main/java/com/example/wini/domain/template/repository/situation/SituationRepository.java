package com.example.wini.domain.template.repository.situation;

import com.example.wini.domain.template.domain.Situation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SituationRepository
    extends JpaRepository<Situation, Long>, SituationCustomRepository {}
