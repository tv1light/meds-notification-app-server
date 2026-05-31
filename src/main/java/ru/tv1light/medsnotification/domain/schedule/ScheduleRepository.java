// src/main/java/ru/tv1light/medsnotification/domain/schedule/ScheduleRepository.java
package ru.tv1light.medsnotification.domain.schedule;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByCourseIdOrderByRemindTimeAsc(Long courseId);
}