package com.okit.authCore.services;

import com.okit.authCore.dto.*;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService
{
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
    ValidateResponse validate(ValidateRequest request);
}
