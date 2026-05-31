// src/main/java/ru/tv1light/medsnotification/domain/dictionary/activesubstance/ActiveSubstance.java
package ru.tv1light.medsnotification.domain.dictionary.activesubstance;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "active_substances")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ActiveSubstance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    @Size(min = 1, max = 50)
    private String name;
}