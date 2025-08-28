package com.example.wini.domain.notification.event;

import com.example.wini.domain.notification.sender.NotificationSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationEventListener {

    private final NotificationSender notificationSender;

    @TransactionalEventListener
    @Async("notificationExecutor")
    public void handleNotificationEvent(NotificationEvent event) {
        notificationSender.send(event);
    }
}
