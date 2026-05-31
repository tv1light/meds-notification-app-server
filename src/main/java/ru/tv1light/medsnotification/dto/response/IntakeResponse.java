// src/main/java/ru/tv1light/medsnotification/dto/response/IntakeResponse.java
package ru.tv1light.medsnotification.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.tv1light.medsnotification.domain.intake.IntakeAction;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
public class IntakeResponse {
    private Long id;
    private Long reminderId;
    private Long userId;
    private String medicationName;
    private IntakeAction action;
    private OffsetDateTime actionedAt;
    private OffsetDateTime snoozedTo;
}