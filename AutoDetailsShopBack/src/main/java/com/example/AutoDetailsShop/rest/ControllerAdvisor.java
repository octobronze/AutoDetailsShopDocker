package com.example.AutoDetailsShop.rest;

import com.example.AutoDetailsShop.domain.ErrorResponse;
import com.example.AutoDetailsShop.exceptions.AlreadyExistsException;
import com.example.AutoDetailsShop.exceptions.NoDataException;
import com.example.AutoDetailsShop.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
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

    @ExceptionHandler(NoDataException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ErrorResponse handleNoDataException(NoDataException noDataException){
        return ErrorResponse.builder()
                .message(noDataException.getMessage())
                .httpStatus(HttpStatus.NO_CONTENT)
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

}
