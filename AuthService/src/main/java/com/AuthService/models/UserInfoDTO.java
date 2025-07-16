package com.AuthService.models;

import com.AuthService.Entity.UserInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;


@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfoDTO extends UserInfo
{

    @NotNull(message="Username is Required")
    @Size(min=3,max=50,message = "Username should be hav atleast 3 and atmost 5 letters")
    private String username;

    @NotNull(message = "FirstName is required")
    private String firstName;
    
    @NotBlank(message = "LastName is required")
    private String lastName;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
    
    private Long phoneNumber;
    
    @Email(message="Email should be valid")
    private String email;
    
}
