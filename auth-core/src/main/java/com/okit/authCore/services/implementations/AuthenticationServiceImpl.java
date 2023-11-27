package com.okit.authCore.services.implementations;

import com.okit.authCore.dto.*;
import com.okit.authCore.exceptions.EmailNotFoundException;
import com.okit.authCore.models.Role;
import com.okit.authCore.models.User;
import com.okit.authCore.repositories.RoleRepository;
import com.okit.authCore.repositories.UserRepository;
import com.okit.authCore.services.AuthenticationService;
import com.okit.authCore.services.JwtService;
import com.okit.authCore.exceptions.GenericException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.okit.authCore.dto.StatusCode.*;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService
{
    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        Set<String> roleString = request.getRoles();
        Set<Role> roles = roleRepository.findByName(roleString);

        if(userRepository.findByEmail(request.getEmail()).isPresent())
            throw new GenericException("email " + request.getEmail() + " already registered", ERROR.statusCode);

        User user = User.builder()
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .roles(roles)
                .build();

        userRepository.save(user);
        logger.info("user {} saved in database", request.getEmail());

        HashMap<String, Object> claims = new HashMap<>()
        {{
            put("roles", roleString);
        }};

        String token = jwtService.generateJwt(claims,user);

        return new AuthenticationResponse(
                "user registered successfully",
                REGISTER.statusCode,
                Map.of("token", token)
            );
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

        User user = userRepository.findByEmail(email)
                        .orElseThrow(() -> new GenericException("email " + email + " not found", ERROR.statusCode));
        logger.info("found user with username {}", email);

        System.out.println("Before authenticate");


        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email,
                        password
                )
        );

        System.out.println("After authenticate");

        List<String> roles = userRepository.findRoles(email);
        HashMap<String, Object> claims = new HashMap<>(){{
            put("roles", roles);
        }};

        String token = jwtService.generateJwt(claims,user);

        return new AuthenticationResponse(
            String.format("user %s authenticated successfully", request.getEmail()), AUTHENTICATE.statusCode, Map.of("token", token)
        );
    }

    @Override
    public ValidateResponse validate(ValidateRequest request)
    {
        String token = request.getToken();
        String email = jwtService.extractEmail(token);

        UserDetails userDetails = userRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException(email));

        return ValidateResponse.builder()
                .valid(jwtService.isJwtValid(token, userDetails))
                .email(email)
                .build();
    }

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final RoleRepository roleRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final AuthenticationManager authenticationManager;

    private final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
}
