package com.suvash.chirkutt.Dto.Response;

import lombok.Data;

@Data
public class CustomErrorResponseDto {
    private String message;
    private int statusCode;

    public CustomErrorResponseDto(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
