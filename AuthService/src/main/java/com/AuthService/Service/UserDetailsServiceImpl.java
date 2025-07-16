package com.AuthService.Service;

import java.util.HashSet;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.AuthService.Entity.UserInfo;
import com.AuthService.EventProducer.UserInfoEvent;
import com.AuthService.EventProducer.UserInfoProducer;
import com.AuthService.Exception.ResourceNotFoundException;
import com.AuthService.Repository.UserRepo;
import com.AuthService.models.UserInfoDTO;

import lombok.AllArgsConstructor;
import lombok.Data;


@Component
@AllArgsConstructor
@Data
public class UserDetailsServiceImpl implements UserDetailsService 
{
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserInfoProducer userInfoProducer;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        UserInfo userInfo=userRepo.findByUsername(username).orElseThrow(()->new ResourceNotFoundException("User", "username", username));
        
        return new CustomUserDetails(userInfo);
    }

    public boolean checkIfUserAlreadyExists(UserInfoDTO userInfoDTO)
    {
        String username=userInfoDTO.getUsername();
        return userRepo.findByUsername(username).isPresent();
    }

    public Boolean signUpUser(UserInfoDTO userInfoDto)
    {
        userInfoDto.setPassword(passwordEncoder.encode(userInfoDto.getPassword()));
        if(checkIfUserAlreadyExists(userInfoDto))
        {
            return false;
        }
        String userId=UUID.randomUUID().toString();
        UserInfo userInfo=new UserInfo(userId,userInfoDto.getUsername(),
                                        userInfoDto.getFirstName(),
                                        userInfoDto.getLastName(),
                                        userInfoDto.getPassword(),
                                        userInfoDto.getPhoneNumber(),
                                        userInfoDto.getEmail(),
                                        new HashSet<>());
        userRepo.save(userInfo);
        userInfoProducer.sendEventToKafka(transformToUserInfoEvent(userInfoDto));
        return true;
    }

    private UserInfoEvent transformToUserInfoEvent(UserInfoDTO userDto)
    {
        return UserInfoEvent.builder()
                .userId(userDto.getUserId())
                .userName(userDto.getUsername())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .phoneNumber(userDto.getPhoneNumber())
                .email(userDto.getEmail())
                .build();
    }
    
}
