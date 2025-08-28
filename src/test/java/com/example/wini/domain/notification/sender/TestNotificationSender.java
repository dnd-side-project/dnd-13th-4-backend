package com.example.wini.domain.notification.sender;

import com.example.wini.domain.notification.event.NotificationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Profile("test")
@Component
public class TestNotificationSender implements NotificationSender {

    @Override
    public void send(NotificationEvent event) {
        log.info("Test Event Listener 동작 성공");
    }
}
