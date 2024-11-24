package com.suvash.chirkutt.Dto.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PasswordRecoveryDto {
    @NotBlank(message = "Usarname must not be blank.")
    private String username;
}
