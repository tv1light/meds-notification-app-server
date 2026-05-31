// src/main/java/ru/tv1light/medsnotification/dto/response/ScheduleResponse.java
package ru.tv1light.medsnotification.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
public class ScheduleResponse {
    private Long id;
    private Long courseId;
    private String medicationName;
    private LocalTime remindTime;
    private OffsetDateTime createdAt;
}