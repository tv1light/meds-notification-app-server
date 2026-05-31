package ru.tv1light.medsnotification.notification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/** Profile=fcm: здесь будет Firebase Admin SDK */
@Slf4j
@Service
@Profile("fcm")
public class FcmNotificationStub implements NotificationService {

    @Override
    public void sendReminder(Long userId, String title, String body) {
        // TODO: FirebaseMessaging.getInstance().send(...)
        log.info("[FCM STUB] userId={} | {} — {}", userId, title, body);
    }
}
