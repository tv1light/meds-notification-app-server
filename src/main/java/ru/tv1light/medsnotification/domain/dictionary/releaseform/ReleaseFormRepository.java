// src/main/java/ru/tv1light/medsnotification/domain/dictionary/releaseform/ReleaseFormRepository.java
package ru.tv1light.medsnotification.domain.dictionary.releaseform;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReleaseFormRepository extends JpaRepository<ReleaseForm, Long> {
    boolean existsByName(String name);
    long count();
}