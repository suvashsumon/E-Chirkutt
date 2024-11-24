package com.suvash.chirkutt.Dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class PasswordChangeDto {
    @NotBlank(message = "New Password field must not be blank.")
    private String newPassword;
}
