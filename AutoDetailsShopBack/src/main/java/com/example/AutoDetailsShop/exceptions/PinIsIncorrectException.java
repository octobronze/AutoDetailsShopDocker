package com.example.AutoDetailsShop.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PinIsIncorrectException extends Exception{
    private String message;
}
