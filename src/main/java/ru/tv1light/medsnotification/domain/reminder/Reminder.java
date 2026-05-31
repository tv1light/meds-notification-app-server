// src/main/java/ru/tv1light/medsnotification/domain/reminder/Reminder.java
package ru.tv1light.medsnotification.domain.reminder;

import jakarta.persistence.*;
import lombok.*;
import ru.tv1light.medsnotification.domain.schedule.Schedule;

import java.time.OffsetDateTime;

@Entity
@Table(name = "reminders")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Reminder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    @Column(name = "remind_at", nullable = false)
    private OffsetDateTime remindAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    private ReminderStatus status = ReminderStatus.PENDING;
}