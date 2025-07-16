package com.AuthService.EventProducer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserInfoEvent 
{
    private String userId;
    private String userName;
    private String firstName;
    private String lastName;
    private Long phoneNumber;
    private String email;
    
}
