package com.example.controller;

import com.example.dto.request.LoginRequest;
import com.example.dto.request.UserRequest;
import com.example.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")  // http://localhost:8080/auth/login
    public ResponseEntity<String>authenticateUser(@RequestBody @Valid LoginRequest loginRequest){
        return authenticationService.authenticateUser(loginRequest);
    }

    @PostMapping("/save/{userRole}")  // http://localhost:8080/auth/save/Customer
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<String> saveAdmin(@RequestBody @Valid UserRequest userRequest,
                                            @PathVariable String userRole){
        return ResponseEntity.ok(authenticationService.saveUser(userRequest,userRole));
    }


}
