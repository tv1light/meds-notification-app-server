// src/main/java/ru/tv1light/medsnotification/domain/reminder/ReminderRepository.java
package ru.tv1light.medsnotification.domain.reminder;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {
    List<Reminder> findAllByScheduleIdOrderByRemindAtAsc(Long scheduleId);

    // Все PENDING напоминания до текущего момента — для планировщика
    List<Reminder> findAllByStatusAndRemindAtBefore(ReminderStatus status, OffsetDateTime before);
}