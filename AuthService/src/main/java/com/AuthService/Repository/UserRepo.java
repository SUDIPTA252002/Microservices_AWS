package com.AuthService.Repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.AuthService.Entity.UserInfo;

@Repository
public interface UserRepo extends CrudRepository<UserInfo,Long> 
{
    Optional<UserInfo> findByUsername(String username);
    
}
