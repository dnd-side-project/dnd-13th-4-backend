package com.example.wini.domain.notification.event;

import com.example.wini.domain.notification.domain.NotificationType;

public record NotificationEvent(Long recipientId, NotificationType notificationType) {

    public static NotificationEvent from(Long recipientId, NotificationType notificationType) {
        return new NotificationEvent(recipientId, notificationType);
    }
}
