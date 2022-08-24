package com.example.demo.exception.EveryExceptions;

import com.example.demo.exception.ErrorType;
import lombok.Getter;

@Getter
public class IllegalArgumentException extends RuntimeException{

    private final ErrorType errorType;

    public IllegalArgumentException(ErrorType errorType) {
        this.errorType = errorType;
    }
}
