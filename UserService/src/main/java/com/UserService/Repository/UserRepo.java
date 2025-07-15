package com.UserService.Repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.UserService.Entity.UserInfo;

@Repository
public interface UserRepo extends CrudRepository<UserInfo,String> {
    Optional<UserInfo> findByUserId(String userId);
}
