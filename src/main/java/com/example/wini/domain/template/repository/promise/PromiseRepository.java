package com.example.wini.domain.template.repository.promise;

import com.example.wini.domain.template.domain.Promise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromiseRepository extends JpaRepository<Promise, Long>, PromiseCustomRepository {}
