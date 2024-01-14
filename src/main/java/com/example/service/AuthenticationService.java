package com.example.service;

import com.example.dto.request.LoginRequest;
import com.example.dto.request.UserRequest;
import com.example.entity.Portfolio;
import com.example.entity.User;
import com.example.entity.enums.RoleType;
import com.example.exception.ConflictException;
import com.example.payload.mappers.UserMapper;
import com.example.payload.messages.ErrorMessages;
import com.example.payload.messages.SuccessMessages;
import com.example.repository.UserRepository;
import com.example.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    public final JwtUtils jwtUtils;
    public final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final UserRoleService userRoleService;

    public ResponseEntity<String> authenticateUser(LoginRequest loginRequest){
        //!!! Gelen requestin icinden kullanici adi ve parola bilgisi aliniyor
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        // !!! authenticationManager uzerinden kullaniciyi valide ediyoruz
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));

        // !!! valide edilen kullanici Context e atiliyor
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // !!! JWT token olusturuluyor
        String token = jwtUtils.generateJtwToken(authentication);

        return ResponseEntity.ok(token);
    }


    // Not : saveUser() *************************************************************
    public String saveUser(UserRequest userRequest, String userRole) {
        // !!! Girilen username unique mi kontrolu
        checkDuplicate(userRequest.getUsername());
        User user = userMapper.mapUserRequestToUser(userRequest); // payload.mapper
        if (userRole.equalsIgnoreCase(RoleType.ADMIN.name())) {

            // !!! admin rolu veriliyor
            user.setUserRole(userRoleService.getUserRole(RoleType.ADMIN));
        } else {
            user.setUserRole(userRoleService.getUserRole(RoleType.CUSTOMER));
        }

        //!!! password encode ediliyor
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // kullaniciya ait portofolio olusturuluyor
        Portfolio portfolio = new Portfolio();
        user.setPortfolio(portfolio);

        userRepository.save(user);

        return SuccessMessages.USER_CREATE;
    }

    public void checkDuplicate(String username ) {
        if (userRepository.existsByUsername(username) ){
            throw new ConflictException(String.format(
                    ErrorMessages.ALREADY_REGISTER_MESSAGE_USERNAME, username));
        }
    }
}
