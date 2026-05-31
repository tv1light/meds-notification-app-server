// src/main/java/ru/tv1light/medsnotification/dto/response/CountryResponse.java
package ru.tv1light.medsnotification.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CountryResponse {
    private Long id;
    private String name;
}