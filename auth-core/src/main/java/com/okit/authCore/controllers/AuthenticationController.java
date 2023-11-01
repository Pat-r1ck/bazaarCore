package com.okit.authCore.controllers;

import com.okit.authCore.dto.*;
import com.okit.authCore.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController
{
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    )
    {
        logger.info("registering user with email {}", request.getEmail());
        return new ResponseEntity<>(authService.register(request), HttpStatus.OK);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    )
    {
        logger.info("authenticating user with email {}", request.getEmail());
        return new ResponseEntity<>(authService.authenticate(request), HttpStatus.OK);
    }

    @PostMapping("/validate")
    public ResponseEntity<ValidateResponse> validate(
            @RequestBody ValidateRequest request
    )
    {
        logger.info("validating token");
        return new ResponseEntity<>(authService.validate(request), HttpStatus.OK);
    }

    @Autowired
    private final AuthenticationService authService;

    private final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
}
