// src/main/java/ru/tv1light/medsnotification/dto/response/ManufacturerResponse.java
package ru.tv1light.medsnotification.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ManufacturerResponse {
    private Long id;
    private String name;
    private Long countryId;
    private String countryName;
}