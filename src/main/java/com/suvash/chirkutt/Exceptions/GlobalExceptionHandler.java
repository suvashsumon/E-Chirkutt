package com.suvash.chirkutt.Exceptions;

import com.suvash.chirkutt.Dto.Response.CustomErrorResponseDto;
import com.suvash.chirkutt.Dto.Response.UserNotFoundExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<UserNotFoundExceptionResponse> UserNotFoundExceptionHandler(UserNotFoundException e)
    {
        UserNotFoundExceptionResponse response = new UserNotFoundExceptionResponse(HttpStatus.NOT_FOUND, "User not found.");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<?> loginDtoValidationHandler(MethodArgumentNotValidException ex, HttpServletRequest req)
    {
        List<String> errors = new ArrayList<>();

        Map<String, String> result = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(err -> result.put(((FieldError)err).getField(), err.getDefaultMessage()));

        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<?>httpMessageNotReadableException(HttpMessageNotReadableException ex)
    {

        return new ResponseEntity<>(
                new CustomErrorResponseDto("The request payload contains invalid syntax or is improperly formatted.", HttpStatus.BAD_REQUEST.value()),
                HttpStatus.BAD_REQUEST
        );
    }
}
