package com.suvash.chirkutt.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MessageDto {
    @NotBlank(message = "Message field must not be blank.")
    @Size(max = 250, message = "Message length must not be more than 250 characters.")
    private String message;

    private String senderinfo;

    @NotBlank(message = "Username field must not be blank.")
    private String username;
}
