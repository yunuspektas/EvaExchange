package com.example.payload.mappers;


import com.example.dto.request.UserRequest;
import com.example.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {


    public User mapUserRequestToUser(UserRequest userRequest){
        return User.builder()
                .username(userRequest.getUsername())
                .password(userRequest.getPassword())
                .build();
    }


}
