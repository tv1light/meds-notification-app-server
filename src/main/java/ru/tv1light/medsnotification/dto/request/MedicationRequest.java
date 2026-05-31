// src/main/java/ru/tv1light/medsnotification/dto/request/MedicationRequest.java
package ru.tv1light.medsnotification.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import ru.tv1light.medsnotification.domain.medication.DosageUnit;

import java.math.BigDecimal;

@Data
public class MedicationRequest {

    @NotBlank
    @Size(min = 2, max = 50, message = "Название препарата: от 2 до 50 символов")
    private String tradeName;

    @NotNull
    private Long activeSubstanceId;

    @NotNull
    private Long releaseFormId;

    @NotNull
    private Long manufacturerId;

    @NotNull
    @DecimalMin(value = "0.001", message = "Минимальная дозировка — 1 мкг")
    @DecimalMax(value = "100000.000", message = "Максимальная дозировка — 100 г")
    private BigDecimal dosageValue;

    @NotNull(message = "Укажите единицу дозировки")
    private DosageUnit dosageUnit;
}