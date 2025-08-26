package com.example.wini.domain.auth.repository;

import com.example.wini.domain.auth.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {}
