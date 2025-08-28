package com.example.wini.domain.notification.event;

import com.example.wini.domain.notification.domain.NotificationType;
import java.util.Optional;

public record NotificationEvent(Long recipientId, NotificationType notificationType, Optional<Long> entityId) {

    public static NotificationEvent from(Long recipientId, NotificationType notificationType) {
        return new NotificationEvent(recipientId, notificationType, Optional.empty());
    }

    public static NotificationEvent from(Long recipientId, NotificationType notificationType, Long id) {
        return new NotificationEvent(recipientId, notificationType, Optional.of(id));
    }
}
