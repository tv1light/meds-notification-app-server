// src/main/java/ru/tv1light/medsnotification/dto/request/CourseRequest.java
package ru.tv1light.medsnotification.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CourseRequest {

    @NotNull
    private Long userId;

    @NotNull
    private Long medicationId;

    @NotNull
    private LocalDate startDate;

    private LocalDate endDate;   // опционально

    private String notes;
}