package com.example.service;

import com.example.dto.UserRequest;
import com.example.entity.User;
import com.example.entity.enums.RoleType;
import com.example.exception.ConflictException;
import com.example.exception.ResourceNotFoundException;
import com.example.payload.mappers.UserMapper;
import com.example.payload.messages.ErrorMessages;
import com.example.payload.messages.SuccessMessages;
import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public long countUserWithRole(RoleType roleType) {
        return userRepository.countUserWithRole(roleType);
    }

    public User findById( Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
