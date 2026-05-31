// src/main/java/ru/tv1light/medsnotification/domain/medication/MedicationRepository.java
package ru.tv1light.medsnotification.domain.medication;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicationRepository extends JpaRepository<Medication, Long> {
    List<Medication> findAllByOrderByTradeNameAsc();
    List<Medication> findAllByOrderByTradeNameDesc();
}