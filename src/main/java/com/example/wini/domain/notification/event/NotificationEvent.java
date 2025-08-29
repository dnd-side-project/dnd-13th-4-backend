package com.example.wini.domain.notification.event;

import com.example.wini.domain.notification.domain.NotificationType;
import java.util.Optional;

public record NotificationEvent(
        Long recipientId, NotificationType notificationType, Optional<Long> entityId, Optional<String> bodyArg) {

    public static NotificationEvent from(Long recipientId, NotificationType notificationType) {
        return new NotificationEvent(recipientId, notificationType, Optional.empty(), Optional.empty());
    }

    public static NotificationEvent from(Long recipientId, NotificationType notificationType, Long id) {
        return new NotificationEvent(recipientId, notificationType, Optional.of(id), Optional.empty());
    }

    public static NotificationEvent from(Long recipientId, NotificationType type, String bodyArg) {
        return new NotificationEvent(recipientId, type, Optional.empty(), Optional.of(bodyArg));
    }
}
