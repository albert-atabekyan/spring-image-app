package com.atabekyan.image.service.controllers;

import com.atabekyan.image.service.requests.AuthenticationRequest;
import com.atabekyan.image.service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/login")
public class LoginController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final BCryptPasswordEncoder encoder;



    @Autowired
    LoginController(UserService userService,
                    BCryptPasswordEncoder encoder,
                    AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
    }

    
    
    @PostMapping
    private ResponseEntity<?> login (@RequestBody AuthenticationRequest authenticationRequest){
        if(userService.isUserIsNotInDb(authenticationRequest.getUsername()))
            return ResponseEntity.ok(101);

        if(isPasswordHashIsNotEqual(authenticationRequest))
            return ResponseEntity.ok(102);

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword());

        final Authentication authentication = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.ok(authentication.getAuthorities());
    }

    private boolean isPasswordHashIsNotEqual(AuthenticationRequest authenticationRequest) {
        String passwordFromRequest = authenticationRequest.getPassword();

        String passwordInDatabase = userService.
                loadUserByUsername(authenticationRequest.getUsername())
                .getPassword();

        return !encoder.matches(passwordFromRequest, passwordInDatabase);
    }
}