package com.example.wini.domain.member.repository;

import com.example.wini.domain.member.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {}
