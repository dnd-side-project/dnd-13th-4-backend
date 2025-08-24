package com.example.wini.infra.fcm.client;

import com.example.wini.domain.notification.domain.NotificationToken;
import com.example.wini.domain.notification.domain.NotificationType;
import com.example.wini.domain.notification.repository.NotificationTokenRepository;
import com.example.wini.domain.notification.sender.NotificationSender;
import com.example.wini.infra.fcm.converter.FcmMessageConverter;
import com.example.wini.infra.fcm.dto.FcmMessageRequest;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MessagingErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class FcmClient implements NotificationSender {

    private final NotificationTokenRepository notificationTokenRepository;
    private final FirebaseMessaging firebaseMessaging;
    private final FcmMessageConverter fcmMessageConverter;

    @Override
    @Transactional
    public void send(Long recipientId, NotificationType notificationType) {
        List<NotificationToken> notificationTokens = notificationTokenRepository.findAllByMember_Id(recipientId);

        if (notificationTokens.isEmpty()) {
            log.info("알림 발송 대상 없음 - memberId: {}", recipientId);
            return;
        }

        notificationTokens.forEach(notificationToken -> pushNotification(notificationToken, notificationType));
    }

    private void pushNotification(NotificationToken notificationToken, NotificationType notificationType) {
        FcmMessageRequest request = FcmMessageRequest.from(notificationToken.getToken(), notificationType);
        Message message = fcmMessageConverter.convert(request);
        try {
            firebaseMessaging.send(message);
        } catch (FirebaseMessagingException e) {
            handleFirebaseMessagingException(e, notificationToken);
        }
    }

    private void handleFirebaseMessagingException(FirebaseMessagingException e, NotificationToken notificationToken) {
        MessagingErrorCode errorCode = e.getMessagingErrorCode();
        if (errorCode == MessagingErrorCode.UNREGISTERED) {
            notificationTokenRepository.delete(notificationToken);
        } else {
            log.error(
                    "알림 발송 실패. token: {}, errorCode: {}, error: {}",
                    notificationToken.getToken(),
                    errorCode,
                    e.getMessage());
        }
    }
}
