package com.suvash.chirkutt.Dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ResetNewPasswordDto {
    @NotBlank(message = "Username must not be blank.")
    private String username;
    @NotBlank(message = "New Password field must not be blank.")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must contain at least 8 characters, including one uppercase letter, one number, and one special character."
    )
    private String newPassword;
    @NotBlank(message = "Token field must not be blank.")
    private String token;
}
