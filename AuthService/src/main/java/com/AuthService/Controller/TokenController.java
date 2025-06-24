package com.AuthService.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.AuthService.Entity.RefreshToken;
import com.AuthService.Entity.UserInfo;
import com.AuthService.Request.AuthRequestDTO;
import com.AuthService.Request.RefreshTokenRequestDTO;
import com.AuthService.Response.JWTResponseDTO;
import com.AuthService.Service.JWTServcie;
import com.AuthService.Service.RefreshTokenService;

@RestController
@RequestMapping("auth/v1")
public class TokenController 
{
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private JWTServcie jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDTO request)
    {
        Authentication authentication=authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
        if(authentication.isAuthenticated())
        {
            RefreshToken refreshToken=refreshTokenService.createRefreshToken(request.getUsername());
            String token=jwtService.generateToken(request.getUsername());

            return new ResponseEntity<>(JWTResponseDTO.builder().RefreshToken(refreshToken.getRefreshhToken()).accessToken(token),HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Exception in User Service",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequestDTO request)
    {
        RefreshToken refToken=refreshTokenService.findToken(request.getToken());
        RefreshToken verifiedToken=refreshTokenService.verifyExpiration(refToken);
        UserInfo userInfo=verifiedToken.getUserInfo();
        
        String accessToken=jwtService.generateToken(userInfo.getUsername());

        JWTResponseDTO jwtResponseDTO=JWTResponseDTO.builder()
                                                    .accessToken(accessToken)
                                                    .RefreshToken(request.getToken())
                                                    .build();

        return new ResponseEntity<>(jwtResponseDTO,HttpStatus.OK);
        
    }
    
}
