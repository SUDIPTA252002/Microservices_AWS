package com.UserService.Config;

import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UserConfig 
{
    @Bean
    public ObjectMapper objectMapperInit()
    {
        return new ObjectMapper();
    }
    
}
