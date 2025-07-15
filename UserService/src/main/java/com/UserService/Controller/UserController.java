package com.UserService.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.UserService.Payloads.UserDTO;
import com.UserService.Service.UserService;

@RestController
@RequestMapping("/user/v1")
public class UserController 
{

    private UserService userService;
    
    @GetMapping("/getUser")
    public ResponseEntity<UserDTO> getUser(@RequestBody UserDTO userDto)
    {
        UserDTO user=userService.getUser(userDto);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @PostMapping("/createUpdate")
    public ResponseEntity<UserDTO> createUpdateUser(@RequestBody UserDTO userDto)
    {
        UserDTO user=userService.createOrUpdateUser(userDto);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @GetMapping("/health")
    public ResponseEntity<Boolean> checkHealth()
    {
        return new ResponseEntity<>(true,HttpStatus.OK);
    }

    
}
