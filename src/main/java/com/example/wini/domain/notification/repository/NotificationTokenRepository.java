package com.example.wini.domain.notification.repository;

import com.example.wini.domain.notification.domain.NotificationToken;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationTokenRepository extends JpaRepository<NotificationToken, Long> {
    void deleteByToken(String token);

    List<NotificationToken> findAllByMember_Id(Long memberId);
}
