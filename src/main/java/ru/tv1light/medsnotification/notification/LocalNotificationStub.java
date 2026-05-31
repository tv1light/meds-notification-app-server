package ru.tv1light.medsnotification.notification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/** Profile=local: сервер только логирует, мобилка сама триггерит */
@Slf4j
@Service
@Profile({"local", "default"})
public class LocalNotificationStub implements NotificationService {

    @Override
    public void sendReminder(Long userId, String title, String body) {
        log.info("[LOCAL STUB] userId={} | {} — {}", userId, title, body);
    }
}
