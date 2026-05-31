// src/main/java/ru/tv1light/medsnotification/dto/request/IntakeRequest.java
package ru.tv1light.medsnotification.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class IntakeRequest {

    @NotNull
    private Long reminderId;

    @NotNull
    private Long userId;

    @NotNull(message = "Укажите действие: DONE, SKIPPED или SNOOZED")
    private ru.tv1light.medsnotification.domain.intake.IntakeAction action;

    // Обязателен только при action=SNOOZED
    private OffsetDateTime snoozedTo;
}