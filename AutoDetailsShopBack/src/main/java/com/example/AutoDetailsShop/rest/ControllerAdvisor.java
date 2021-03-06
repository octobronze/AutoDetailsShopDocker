package com.example.AutoDetailsShop.rest;

import com.example.AutoDetailsShop.domain.ErrorResponse;
import com.example.AutoDetailsShop.exceptions.AlreadyExistsException;
import com.example.AutoDetailsShop.exceptions.ExpirationException;
import com.example.AutoDetailsShop.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.AutoDetailsShop.exceptions.ValidationException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(ValidationException validationException){
        return ErrorResponse.builder()
                .message(validationException.getMessage())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(NotFoundException notFoundException){
        return ErrorResponse.builder()
                .message(notFoundException.getMessage())
                .httpStatus(HttpStatus.NOT_FOUND)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(HttpStatus.FOUND)
    public ErrorResponse handleAlreadyExistsException(AlreadyExistsException alreadyExistsException){
        return ErrorResponse.builder()
                .message(alreadyExistsException.getMessage())
                .httpStatus(HttpStatus.FOUND)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleAuthenticationException(AuthenticationException authenticationException){
        return ErrorResponse.builder()
                .message(authenticationException.getMessage())
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(ExpirationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleExpirationException(ExpirationException expirationException){
        return ErrorResponse.builder()
                .message(expirationException.getMessage())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .timestamp(LocalDateTime.now())
                .build();
    }

}
