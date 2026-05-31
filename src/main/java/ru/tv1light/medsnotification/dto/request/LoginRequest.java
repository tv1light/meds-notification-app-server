package ru.tv1light.medsnotification.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank
    @Size(min = 4, max = 12)
    private String login;

    @NotBlank
    @Size(min = 8, max = 14)
    private String password;
}
