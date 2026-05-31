// src/main/java/ru/tv1light/medsnotification/domain/intake/Intake.java
package ru.tv1light.medsnotification.domain.intake;

import jakarta.persistence.*;
import lombok.*;
import ru.tv1light.medsnotification.domain.reminder.Reminder;
import ru.tv1light.medsnotification.domain.user.User;

import java.time.OffsetDateTime;

@Entity
@Table(name = "intakes")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Intake {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "reminder_id", nullable = false)
    private Reminder reminder;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "action", nullable = false, length = 20)
    private IntakeAction action;

    @Column(name = "actioned_at", nullable = false, updatable = false)
    private OffsetDateTime actionedAt;

    @Column(name = "snoozed_to")
    private OffsetDateTime snoozedTo;   // заполняется только при SNOOZED

    @PrePersist
    private void prePersist() {
        actionedAt = OffsetDateTime.now();
    }
}