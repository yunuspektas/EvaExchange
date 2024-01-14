package com.example.service;

import com.example.entity.User;
import com.example.entity.enums.RoleType;
import com.example.exception.ResourceNotFoundException;
import com.example.payload.messages.ErrorMessages;
import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public long countUserWithRole(RoleType roleType) {
        return userRepository.countUserWithRole(roleType);
    }

    public User findById( Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.NOT_FOUND_USER_MESSAGE));
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username).orElseThrow(()->
                new ResourceNotFoundException(ErrorMessages.NOT_FOUND_USER_MESSAGE));
    }
}
