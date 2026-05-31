// src/main/java/ru/tv1light/medsnotification/domain/intake/IntakeRepository.java
package ru.tv1light.medsnotification.domain.intake;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IntakeRepository extends JpaRepository<Intake, Long> {
    List<Intake> findAllByUserIdOrderByActionedAtDesc(Long userId);
    List<Intake> findAllByUserIdOrderByActionedAtAsc(Long userId);
}