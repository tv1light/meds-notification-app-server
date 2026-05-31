// src/main/java/ru/tv1light/medsnotification/dto/request/ReminderRequest.java
package ru.tv1light.medsnotification.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class ReminderRequest {

    @NotNull
    private Long scheduleId;

    @NotNull(message = "Укажите дату и время напоминания")
    private OffsetDateTime remindAt;
}