package com.AuthService.Repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.AuthService.Entity.UserInfo;

public interface UserRepo extends CrudRepository<UserInfo,Long> 
{
    Optional<UserInfo> findByUsername(String username);
    
}
