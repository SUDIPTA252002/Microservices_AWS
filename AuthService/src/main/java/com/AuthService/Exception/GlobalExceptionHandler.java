package com.AuthService.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.AuthService.Response.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler 
{
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundExceptionHandler(ResourceNotFoundException ex)
    {
        return new ResponseEntity<>(new ApiResponse(ex.getMessage(),false),HttpStatus.NOT_FOUND);   
    }
    
}
