package com.AuthService.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTServcie 
{

    private static final String secretKey="a1b2c3d4e5f6g7h8i9j0k1l2m3n4o5p6q7r8s9t0u1v2w3x4y5z6a7b8c9d0e1f2";
    private long Expirtaion=1000*60*60;

    private Claims extractAllClaims(String token)
    {
        return Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
    }
   
    
    private <T> T exctractClaim(String token,Function<Claims,T> claimResolver)
    {
        final Claims claim=extractAllClaims(token);
        return claimResolver.apply(claim);
    }
    
    public String extractUsername(String token)
    {
        return exctractClaim(token,Claims::getSubject);
    }
     public String generateToken(String username){
        Map<String, Object> claims = new HashMap<>();
        return generateToken(claims, username);
    }


    private String generateToken(Map<String,Object> claims,String username)
    {
        return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(username)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis()+Expirtaion))
                    .signWith(getSignInKey(),SignatureAlgorithm.HS256)
                    .compact();
    }

    private Key getSignInKey()
    {
        byte[] keyBytes=Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean isTokenValid(String token,UserDetails userDetails)
    {
        String username=userDetails.getUsername();
        return username.equals(extractUsername(token))&&isTokenExpired(token);
    }

    public boolean isTokenExpired(String token)
    {
        return extractExpiration(token).before(new Date());
    }
    public Date extractExpiration(String token)
    {
        return exctractClaim(token,Claims::getExpiration);
    }
    
}