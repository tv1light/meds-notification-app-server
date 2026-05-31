// src/main/java/ru/tv1light/medsnotification/domain/dictionary/activesubstance/ActiveSubstanceRepository.java
package ru.tv1light.medsnotification.domain.dictionary.activesubstance;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ActiveSubstanceRepository extends JpaRepository<ActiveSubstance, Long> {
    boolean existsByName(String name);
}