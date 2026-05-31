// src/main/java/ru/tv1light/medsnotification/dto/request/ScheduleRequest.java
package ru.tv1light.medsnotification.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalTime;

@Data
public class ScheduleRequest {

    @NotNull
    private Long courseId;

    @NotNull(message = "Укажите время напоминания")
    private LocalTime remindTime;
}