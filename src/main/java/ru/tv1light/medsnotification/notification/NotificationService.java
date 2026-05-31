package ru.tv1light.medsnotification.notification;

public interface NotificationService {
    void sendReminder(Long userId, String title, String body);
}
