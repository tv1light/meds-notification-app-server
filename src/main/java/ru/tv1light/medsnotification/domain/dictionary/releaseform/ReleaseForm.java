// src/main/java/ru/tv1light/medsnotification/domain/dictionary/releaseform/ReleaseForm.java
package ru.tv1light.medsnotification.domain.dictionary.releaseform;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "release_forms")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ReleaseForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    @Size(min = 2, max = 50)
    private String name;
}