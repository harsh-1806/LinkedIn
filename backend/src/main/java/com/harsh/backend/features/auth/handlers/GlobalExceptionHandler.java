package com.harsh.backend.features.auth.handlers;

import com.harsh.backend.features.auth.dtos.responses.ApiResponse;
import com.harsh.backend.features.auth.exceptions.EmailAlreadyExistsException;
import com.harsh.backend.features.auth.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse> handleUserNotFoundException(UserNotFoundException e) {
        ApiResponse response = ApiResponse.builder().message(e.getMessage()).success(Boolean.TRUE).status(HttpStatus.NOT_FOUND).build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ApiResponse response = ApiResponse.builder().message(e.getMessage()).success(Boolean.TRUE).status(HttpStatus.NOT_ACCEPTABLE).build();
        return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    }
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ApiResponse> handleEmailAlreadyExistsException(EmailAlreadyExistsException e) {
        ApiResponse response = ApiResponse.builder().message(e.getMessage()).success(Boolean.TRUE).status(HttpStatus.NOT_ACCEPTABLE).build();
        return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    }
}
