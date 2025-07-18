package com.AuthService.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.AuthService.Entity.RefreshToken;
import com.AuthService.Response.ApiResponse;
import com.AuthService.Response.JWTResponseDTO;
import com.AuthService.Service.JWTServcie;
import com.AuthService.Service.RefreshTokenService;
import com.AuthService.Service.UserDetailsServiceImpl;
import com.AuthService.models.UserInfoDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth/v1")
public class AuthController 
{
    private JWTServcie jwtServcie;
    private RefreshTokenService refreshTokenService;
    private UserDetailsServiceImpl userDetails;


    public AuthController(JWTServcie jwtService, RefreshTokenService refreshTokenService, UserDetailsServiceImpl userDetails) {
        this.jwtServcie = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.userDetails = userDetails;
    }


    @PostMapping(value="/signup",produces = "application/json")
    public ResponseEntity<?> signUp(@Valid@RequestBody UserInfoDTO userInfo)
    {
        try 
        {
            Boolean isSignedUp=userDetails.signUpUser(userInfo);
            if(Boolean.FALSE.equals(isSignedUp))
            {
                return new ResponseEntity<>(new ApiResponse("Alreaady Exists",false),HttpStatus.BAD_REQUEST);
            }
            RefreshToken refreshToken=refreshTokenService.createRefreshToken(userInfo.getUsername());
            String jwtToken=jwtServcie.generateToken(userInfo.getUsername());
            return new ResponseEntity<>(JWTResponseDTO.builder().accessToken(jwtToken).RefreshToken(refreshToken.getRefreshhToken()).build(),HttpStatus.OK);
            
        }
         catch (Exception e) 
        {
             e.printStackTrace();

            System.out.println("Exception occurred in /signup handler: " + e.getClass().getName() + " - " + e.getMessage());

            return new ResponseEntity<>(new ApiResponse("Exception in User Service",false),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
