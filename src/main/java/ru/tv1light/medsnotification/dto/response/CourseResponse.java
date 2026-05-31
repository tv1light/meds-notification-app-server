// src/main/java/ru/tv1light/medsnotification/dto/response/CourseResponse.java
package ru.tv1light.medsnotification.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
public class CourseResponse {
    private Long id;
    private Long userId;
    private String userLogin;
    private Long medicationId;
    private String medicationName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String notes;
    private OffsetDateTime createdAt;
}