package com.AuthService.Repository;


import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.AuthService.Entity.RefreshToken;

@Repository
public interface RefreshTokenRepo extends CrudRepository<RefreshToken,Integer> {
    Optional<RefreshToken> findByrefreshhToken(String refershToken);
}
