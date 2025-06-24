package com.AuthService.Service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.AuthService.Entity.RefreshToken;
import com.AuthService.Entity.UserInfo;
import com.AuthService.Exception.ResourceNotFoundException;
import com.AuthService.Repository.RefreshTokenRepo;
import com.AuthService.Repository.UserRepo;

public class RefreshTokenService 
{
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RefreshTokenRepo refreshTokenRepo;

    public RefreshToken createRefreshToken(String username)
    {

        UserInfo userInfo=userRepo.findByUsername(username).orElseThrow(()->new ResourceNotFoundException("user","usernaname", username));
        RefreshToken refreshToken = RefreshToken.builder()
                    .userInfo(userInfo)
                    .refreshhToken(UUID.randomUUID().toString())
                    .expiryDate(Instant.now().plusMillis(600000))
                    .build();
        
        return refreshTokenRepo.save(refreshToken);
    }

    public RefreshToken findToken(String token)
    {
        return refreshTokenRepo.findByToken(token).orElseThrow(()->new ResourceNotFoundException("Refresh","Token", token));
    }

    public RefreshToken verifyExpiration(RefreshToken token)
    {
        if(token.getExpiryDate().compareTo(Instant.now())<0)
        {
            refreshTokenRepo.delete(token);
            throw new RuntimeException("Refresh Token is expired.Please login again");
        }
        return token;
    }
    
}
