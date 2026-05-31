// src/main/java/ru/tv1light/medsnotification/dto/response/ReleaseFormResponse.java
package ru.tv1light.medsnotification.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReleaseFormResponse {
    private Long id;
    private String name;
}