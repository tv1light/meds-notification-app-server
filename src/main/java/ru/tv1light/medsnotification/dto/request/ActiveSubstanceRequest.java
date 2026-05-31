// src/main/java/ru/tv1light/medsnotification/dto/request/ActiveSubstanceRequest.java
package ru.tv1light.medsnotification.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ActiveSubstanceRequest {

    @NotBlank
    @Size(min = 1, max = 50, message = "Действующее вещество: от 1 до 50 символов")
    private String name;
}