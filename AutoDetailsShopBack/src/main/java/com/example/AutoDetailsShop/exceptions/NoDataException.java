package com.example.AutoDetailsShop.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NoDataException extends Exception{
    private final String message;
}
