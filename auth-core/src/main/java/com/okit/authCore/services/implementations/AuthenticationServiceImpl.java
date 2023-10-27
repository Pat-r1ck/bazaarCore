package com.okit.authCore.services.implementations;

import com.okit.authCore.dto.*;
import com.okit.authCore.exceptions.UsernameNotFoundException;
import com.okit.authCore.models.Role;
import com.okit.authCore.models.User;
import com.okit.authCore.repositories.RoleRepository;
import com.okit.authCore.repositories.UserRepository;
import com.okit.authCore.services.AuthenticationService;
import com.okit.authCore.services.JwtService;
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
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService
{
    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        Set<String> roleString = request.getRoles();
        Set<Role> roles = roleRepository.findByName(roleString);

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .roles(roles)
                .build();

        userRepository.save(user);
        logger.info("user {} saved in database", request.getUsername());

        HashMap<String, Object> claims = new HashMap<>()
        {{
            put("roles", roleString);
        }};

        String token = jwtService.generateJwt(claims,user);

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password
                )
        );

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        logger.info("found user with username {}", username);

        String token = jwtService.generateJwt(user);

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public ValidateResponse validate(ValidateRequest request)
    {
        String token = request.getToken();
        String username = jwtService.extractUsername(token);

        UserDetails userDetails = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return ValidateResponse.builder()
                .valid(jwtService.isJwtValid(token, userDetails))
                .username(username)
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
