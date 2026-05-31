// src/main/java/ru/tv1light/medsnotification/domain/dictionary/manufacturer/ManufacturerRepository.java
package ru.tv1light.medsnotification.domain.dictionary.manufacturer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {
    boolean existsByName(String name);
}