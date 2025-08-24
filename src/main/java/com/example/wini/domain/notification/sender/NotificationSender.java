package com.example.wini.domain.notification.sender;

import com.example.wini.domain.notification.domain.NotificationType;

public interface NotificationSender {

    void send(Long recipientId, NotificationType notificationType);
}
