package com.suvash.chirkutt.Dto.Response;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class UserNotFoundExceptionResponse {
    private int statusCode;
    private String message;

    public UserNotFoundExceptionResponse(HttpStatus httpStatus, String message) {
        this.statusCode = httpStatus.value();
        this.message = message;
    }
}
