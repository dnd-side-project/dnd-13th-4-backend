package com.example.wini.domain.template.repository.emotion;

import com.example.wini.domain.template.domain.Emotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmotionRepository extends JpaRepository<Emotion, Long>, EmotionCustomRepository {}
