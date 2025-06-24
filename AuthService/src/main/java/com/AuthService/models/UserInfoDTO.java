package com.AuthService.models;

import com.AuthService.Entity.UserInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Data;


@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfoDTO extends UserInfo
{

    private String firstName;
    private String lastName;
    private Long phoneNumber;
    private String email;
    
}
