package com.example.wini.domain.notification.repository;

import com.example.wini.domain.member.domain.Member;
import com.example.wini.domain.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    void deleteByMember(Member member);

    void deleteByToken(String token);
}
