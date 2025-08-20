package com.example.wini.domain.template.repository;

import com.example.wini.domain.template.domain.Closing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClosingRepository extends JpaRepository<Closing, Long>, ClosingCustomRepository {}
