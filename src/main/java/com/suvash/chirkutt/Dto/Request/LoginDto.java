package com.suvash.chirkutt.Dto.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDto {

    @NotBlank(message = "Username must not be blank.")
    private String username;
    @NotBlank(message = "Password must not be blank.")
    private String password;

}