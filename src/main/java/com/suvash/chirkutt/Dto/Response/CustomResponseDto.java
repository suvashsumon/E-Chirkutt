package com.suvash.chirkutt.Dto.Response;

import lombok.Data;

@Data
public class CustomResponseDto {
    private String message;
    private int statusCode;

    public CustomResponseDto(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
