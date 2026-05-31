// src/main/java/ru/tv1light/medsnotification/domain/medication/Medication.java
package ru.tv1light.medsnotification.domain.medication;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import ru.tv1light.medsnotification.domain.dictionary.activesubstance.ActiveSubstance;
import ru.tv1light.medsnotification.domain.dictionary.manufacturer.Manufacturer;
import ru.tv1light.medsnotification.domain.dictionary.releaseform.ReleaseForm;

import java.math.BigDecimal;

@Entity
@Table(name = "medications")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "trade_name", nullable = false, length = 50)
    @Size(min = 2, max = 50)
    private String tradeName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "active_substance_id", nullable = false)
    private ActiveSubstance activeSubstance;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "release_form_id", nullable = false)
    private ReleaseForm releaseForm;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "manufacturer_id", nullable = false)
    private Manufacturer manufacturer;

    // 1 мкг = 0.001 мг — минимум 0.001 в единицах мг, проще валидировать на уровне бизнес-логики
    @Column(name = "dosage_value", nullable = false, precision = 10, scale = 3)
    @DecimalMin(value = "0.001", message = "Минимальная дозировка — 1 мкг (0.001 мг)")
    @DecimalMax(value = "100000.000", message = "Максимальная дозировка — 100 г (100000 мг)")
    private BigDecimal dosageValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "dosage_unit", nullable = false, length = 10)
    private DosageUnit dosageUnit;
}