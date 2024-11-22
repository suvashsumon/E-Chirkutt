package com.suvash.chirkutt.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterDto {
    @NotBlank(message = "Name must not be blank.")
    private String name;

    @NotBlank(message = "Email must not be blank.")
    @Email(message = "Email field must be contains valid email.")
    private String email;

    @NotBlank(message = "Username must not be blank.")
    @Size(min = 5, max = 10, message = "Username must be between 5 to 10 character long.")
    private String username;

    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must contain at least 8 characters, including one uppercase letter, one number, and one special character."
    )
    private String password;
}
