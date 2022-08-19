package com.example.AutoDetailsShop.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

@AllArgsConstructor
@Getter
public class NotFoundException extends RuntimeException {
    private final String message;
}
