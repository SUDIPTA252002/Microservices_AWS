package com.UserService.Exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserNotFoundException extends RuntimeException
{
    private String fieldValue;

    public UserNotFoundException(String fieldValue)
    {
        super(String.format("User with userId:%s not found",fieldValue));
        this.fieldValue=fieldValue;
    }
    
}
