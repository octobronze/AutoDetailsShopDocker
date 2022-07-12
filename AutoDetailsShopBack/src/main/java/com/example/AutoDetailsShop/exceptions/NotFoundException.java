package com.example.AutoDetailsShop.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NotFoundException extends Exception{
    private final String message;
}
