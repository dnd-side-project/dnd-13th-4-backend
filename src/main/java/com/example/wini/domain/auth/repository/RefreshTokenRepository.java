package com.example.wini.domain.auth.repository;

import com.example.wini.domain.auth.domain.RefreshToken;
import com.example.wini.domain.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    void deleteByMember(Member member);
}
