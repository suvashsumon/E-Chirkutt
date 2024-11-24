package com.suvash.chirkutt.Dto.Response;

import lombok.Data;

@Data
public class PasswordChangedResponse {
    private String message;
    private int statusCode;

    public PasswordChangedResponse(String message, int statusCode)
    {
        this.message = message;
        this.statusCode = statusCode;
    }
}
