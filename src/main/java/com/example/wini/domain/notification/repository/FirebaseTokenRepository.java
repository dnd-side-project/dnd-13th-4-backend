package com.example.wini.domain.notification.repository;

import com.example.wini.domain.notification.domain.FirebaseToken;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FirebaseTokenRepository extends JpaRepository<FirebaseToken, Long> {
    void deleteByToken(String token);

    List<FirebaseToken> findAllByMember_Id(Long memberId);
}
