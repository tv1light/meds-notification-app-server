// src/main/java/ru/tv1light/medsnotification/domain/schedule/Schedule.java
package ru.tv1light.medsnotification.domain.schedule;

import jakarta.persistence.*;
import lombok.*;
import ru.tv1light.medsnotification.domain.course.Course;

import java.time.LocalTime;
import java.time.OffsetDateTime;

@Entity
@Table(name = "schedules")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(name = "remind_time", nullable = false)
    private LocalTime remindTime;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @PrePersist
    private void prePersist() {
        createdAt = OffsetDateTime.now();
    }
}