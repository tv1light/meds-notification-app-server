// src/main/java/ru/tv1light/medsnotification/dto/response/ActiveSubstanceResponse.java
package ru.tv1light.medsnotification.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ActiveSubstanceResponse {
    private Long id;
    private String name;
}