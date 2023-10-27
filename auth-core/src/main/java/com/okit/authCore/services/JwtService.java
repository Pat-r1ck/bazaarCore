package com.okit.authCore.services;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Function;

@Service
public interface JwtService
{
    String extractUsername(String jwt);
    <T> T extractClaim(String jwt, Function<Claims, T> claimsResolver);
    String generateJwt(Map<String, Object> claims, UserDetails userDetails);
    String generateJwt(UserDetails userDetails);
    boolean isJwtValid(String jwt, UserDetails userDetails);
}
