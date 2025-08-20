package com.example.wini.domain.template.repository.closing;

import com.example.wini.domain.template.domain.Closing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClosingRepository extends JpaRepository<Closing, Long>, ClosingCustomRepository {}
