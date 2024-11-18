package com.suvash.chirkutt.Exceptions;

import com.suvash.chirkutt.Dto.Response.UserNotFoundExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<UserNotFoundExceptionResponse> UserNotFoundExceptionHandler(UserNotFoundException e)
    {
        UserNotFoundExceptionResponse response = new UserNotFoundExceptionResponse(HttpStatus.NOT_FOUND, "User not found.");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
