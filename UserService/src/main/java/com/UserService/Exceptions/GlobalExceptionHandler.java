package com.UserService.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.UserService.Payloads.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler 
{
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse> userNotFoundExceptionHandler(UserNotFoundException ex)
    {
        return new ResponseEntity<>(new ApiResponse(ex.getMessage(),false),HttpStatus.NOT_FOUND);
    }

    
}
