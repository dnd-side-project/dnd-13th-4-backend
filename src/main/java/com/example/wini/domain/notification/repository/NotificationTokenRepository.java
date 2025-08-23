package com.example.wini.domain.notification.repository;

import com.example.wini.domain.notification.domain.NotificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationTokenRepository extends JpaRepository<NotificationToken, Long> {
    void deleteByToken(String token);
}
