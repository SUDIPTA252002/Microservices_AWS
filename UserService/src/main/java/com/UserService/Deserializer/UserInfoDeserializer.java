package com.UserService.Deserializer;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;

import com.UserService.Payloads.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserInfoDeserializer implements Deserializer<UserDTO>
{
    @Override
    public void close()
    {

    }
    @Override
    public void configure(Map<String,?> map, boolean b)
    {

    }

    @Override
    public UserDTO deserialize(String arg0, byte[] arg1) 
    {
        ObjectMapper objectMapper= new ObjectMapper();
        UserDTO user=null;

        try 
        {
            user=objectMapper.readValue(arg1, UserDTO.class);   
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
        
        return user;
    }
    
}
