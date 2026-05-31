// src/main/java/ru/tv1light/medsnotification/dto/request/ManufacturerRequest.java
package ru.tv1light.medsnotification.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ManufacturerRequest {

    @NotBlank
    @Size(min = 2, max = 50, message = "Производитель: от 2 до 50 символов")
    private String name;

    @NotNull(message = "Укажите countryId")
    private Long countryId;
}