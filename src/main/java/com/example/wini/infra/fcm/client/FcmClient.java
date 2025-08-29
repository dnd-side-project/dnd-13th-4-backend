package com.example.wini.infra.fcm.client;

import com.example.wini.domain.notification.domain.FirebaseToken;
import com.example.wini.domain.notification.event.NotificationEvent;
import com.example.wini.domain.notification.repository.FirebaseTokenRepository;
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
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@Profile("!test")
@RequiredArgsConstructor
public class FcmClient implements NotificationSender {

    private final FirebaseTokenRepository firebaseTokenRepository;
    private final FirebaseMessaging firebaseMessaging;
    private final FcmMessageConverter fcmMessageConverter;

    @Override
    @Transactional
    public void send(NotificationEvent event) {
        List<FirebaseToken> firebaseTokens = firebaseTokenRepository.findAllByMember_Id(event.recipientId());

        if (firebaseTokens.isEmpty()) {
            log.info("[FcmClient] 알림 발송 대상 없음 - memberId: {}", event.recipientId());
            return;
        }

        firebaseTokens.forEach(firebaseToken -> pushNotification(firebaseToken, event));
    }

    private void pushNotification(FirebaseToken firebaseToken, NotificationEvent event) {
        FcmMessageRequest request = FcmMessageRequest.from(
                firebaseToken.getToken(), event.notificationType(), event.bodyArg(), event.entityId());
        Message message = fcmMessageConverter.convert(request);
        try {
            firebaseMessaging.send(message);
        } catch (FirebaseMessagingException e) {
            deleteUnregisteredFirebaseToken(e, firebaseToken);
        }
    }

    private void deleteUnregisteredFirebaseToken(FirebaseMessagingException e, FirebaseToken firebaseToken) {
        MessagingErrorCode errorCode = e.getMessagingErrorCode();
        if (errorCode == MessagingErrorCode.UNREGISTERED) {
            firebaseTokenRepository.delete(firebaseToken);
        }
    }
}
