// src/main/java/ru/tv1light/medsnotification/domain/dictionary/manufacturer/Manufacturer.java
package ru.tv1light.medsnotification.domain.dictionary.manufacturer;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import ru.tv1light.medsnotification.domain.dictionary.country.Country;

@Entity
@Table(name = "manufacturers")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Manufacturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    @Size(min = 2, max = 50)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;
}