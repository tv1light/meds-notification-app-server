// src/main/java/ru/tv1light/medsnotification/dto/response/MedicationResponse.java
package ru.tv1light.medsnotification.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.tv1light.medsnotification.domain.medication.DosageUnit;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class MedicationResponse {
    private Long id;
    private String tradeName;
    private Long activeSubstanceId;
    private String activeSubstanceName;
    private Long releaseFormId;
    private String releaseFormName;
    private Long manufacturerId;
    private String manufacturerName;
    private String manufacturerCountry;
    private BigDecimal dosageValue;
    private DosageUnit dosageUnit;
}