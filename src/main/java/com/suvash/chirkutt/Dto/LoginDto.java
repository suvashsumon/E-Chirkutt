package com.suvash.chirkutt.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginDto {

    @NotBlank(message = "Username must not be blank.")
    private String username;
    @NotBlank(message = "Password must not be blank.")
    private String password;

}