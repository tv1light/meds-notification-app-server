// src/main/java/ru/tv1light/medsnotification/dto/request/CountryRequest.java
package ru.tv1light.medsnotification.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CountryRequest {

    @NotBlank
    @Size(min = 3, max = 56, message = "Название страны: от 3 до 56 символов")
    private String name;
}