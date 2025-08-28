package com.example.wini.domain.notification.sender;

import com.example.wini.domain.notification.event.NotificationEvent;

public interface NotificationSender {

    void send(NotificationEvent event);
}
