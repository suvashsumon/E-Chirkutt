package com.suvash.chirkutt.Dto.Response;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class MessageSentResponse {
    public int statusCode;
    public String message;

    public MessageSentResponse(HttpStatus httpStatus, String message) {
        this.statusCode = httpStatus.value();
        this.message = message;
    }
}
