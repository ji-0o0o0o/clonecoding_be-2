package com.example.demo.exception.EveryExceptions;

import com.example.demo.exception.ErrorType;
import lombok.Getter;

@Getter
public class NullPointerException extends RuntimeException {
    private final ErrorType errorType;


    public NullPointerException(ErrorType errorType) {
        this.errorType = errorType;
    }
}
