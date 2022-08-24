package com.example.demo.exception;

import com.example.demo.exception.EveryExceptions.AuthenticationException;
import com.example.demo.exception.EveryExceptions.IllegalArgumentException;
import com.example.demo.exception.EveryExceptions.NullPointerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestApiExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> IllegalArgumentException(IllegalArgumentException illegalArgumentException) {
        ErrorResponse errorResponse = new ErrorResponse(illegalArgumentException.getErrorType().getDescription());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponse> nullPointException(NullPointerException nullPointerException) {
        ErrorResponse errorResponse = new ErrorResponse(nullPointerException.getErrorType().getDescription());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex){
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorType().getDescription());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}