// src/main/java/ru/tv1light/medsnotification/domain/dictionary/country/CountryRepository.java
package ru.tv1light.medsnotification.domain.dictionary.country;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
    boolean existsByName(String name);
}