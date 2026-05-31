// src/main/java/ru/tv1light/medsnotification/dto/request/ReleaseFormRequest.java
package ru.tv1light.medsnotification.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ReleaseFormRequest {

    @NotBlank
    @Size(min = 2, max = 50, message = "Форма выпуска: от 2 до 50 символов")
    private String name;
}