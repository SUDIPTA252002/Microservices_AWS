package com.UserService.Payloads;

import com.UserService.Entity.UserInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO 
{
    @JsonProperty("user_id")
    @NonNull
    private String userId;

    @JsonProperty("user_name")
    private String userName;
    
    @JsonProperty("first_name")
    @NonNull
    private String firstName;
    
    @JsonProperty("last_name")
    @NonNull
    private String lastName;
    
    @JsonProperty("phone_number")
    @NonNull
    private Long phoneNumber;
    
    @JsonProperty("email")
    @NonNull
    @Email
    private String email;

    public UserInfo transforToUserInfo()
    {
        return UserInfo.builder()
                .userName(userName)
                .firstName(firstName)
                .lastName(lastName)
                .userId(userId)
                .phoneNumber(phoneNumber)
                .email(email)
                .build();
    }

    
}
