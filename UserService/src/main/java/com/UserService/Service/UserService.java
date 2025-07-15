package com.UserService.Service;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.UserService.Entity.UserInfo;
import com.UserService.Exceptions.UserNotFoundException;
import com.UserService.Payloads.UserDTO;
import com.UserService.Repository.UserRepo;

@Service
public class UserService 
{
    @Autowired
    private UserRepo userRepo;

    public UserDTO createOrUpdateUser(UserDTO userDto)
    {
        UnaryOperator<UserInfo> updateUser=user->{
            return userRepo.save(userDto.transforToUserInfo());
        };

        Supplier<UserInfo> createUser=()->{
            return userRepo.save(userDto.transforToUserInfo());
        };

        UserInfo userInfo=userRepo.findById(userDto.getUserId())
                            .map(updateUser)
                            .orElseGet(createUser);

        return new UserDTO(userInfo.getUserId(),
                            userInfo.getFirstName(),
                            userInfo.getLastName(),
                            userInfo.getPhoneNumber(),
                            userInfo.getEmail());
    }
    

    public UserDTO getUser(UserDTO userDto)
    {
        UserInfo userInfo=userRepo.findByUserId(userDto.getUserId()).orElseThrow(()->new UserNotFoundException(userDto.getUserId()));
        return new UserDTO(userInfo.getUserId(),userInfo.getFirstName(),userInfo.getLastName(),userInfo.getPhoneNumber(),userInfo.getEmail());
    }
}
