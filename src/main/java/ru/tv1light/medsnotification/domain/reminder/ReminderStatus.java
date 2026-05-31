// src/main/java/ru/tv1light/medsnotification/domain/reminder/ReminderStatus.java
package ru.tv1light.medsnotification.domain.reminder;

public enum ReminderStatus {
    PENDING,   // ещё не наступило / ждёт
    DONE,      // подтверждён
    SKIPPED,   // пропущен
    SNOOZED    // перенесён
}