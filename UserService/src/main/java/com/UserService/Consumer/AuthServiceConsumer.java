package com.UserService.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.UserService.Payloads.UserDTO;
import com.UserService.Service.UserService;

@Service
public class AuthServiceConsumer 
{
    @Autowired
    private UserService userService;
    
    @KafkaListener(topics="${spring.kafka.topic-json.name}",groupId="${spring.kafka.consumer.group-id}")
    public void Listen(UserDTO userDTO)
    {
        try 
        {
            userService.createOrUpdateUser(userDTO);
            
        } 
        catch (Exception ex) 
        {
             ex.printStackTrace();
            System.out.println("AuthServiceConsumer: Exception is thrown while consuming kafka event");
        }
    }
    
}
