// src/main/java/ru/tv1light/medsnotification/dto/response/ReminderResponse.java
package ru.tv1light.medsnotification.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.tv1light.medsnotification.domain.reminder.ReminderStatus;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
public class ReminderResponse {
    private Long id;
    private Long scheduleId;
    private Long courseId;
    private String medicationName;
    private OffsetDateTime remindAt;
    private ReminderStatus status;
}