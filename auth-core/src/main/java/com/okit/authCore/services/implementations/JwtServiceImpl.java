package com.okit.authCore.services.implementations;

import com.okit.authCore.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService
{
    public JwtServiceImpl(Environment env)
    {
        this.env = env;
        this.SECRET_KEY = this.env.getProperty("jwt.secret");
        this.SIGN_IN_KEY = getSignInKey();
    }
    @Override
    public String extractUsername(String jwt)
    {
        return extractClaim(jwt,Claims::getSubject);
    }

    @Override
    public <T> T extractClaim(String jwt, Function<Claims, T> claimsResolver)
    {
        final Claims claims = extractAllClaims(jwt);
        return claimsResolver.apply(claims);
    }

    @Override
    public String generateJwt(Map<String, Object> claims, UserDetails userDetails)
    {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(SIGN_IN_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String generateJwt(UserDetails userDetails)
    {
        return generateJwt(new HashMap<>(), userDetails);
    }

    @Override
    public boolean isJwtValid(String jwt, UserDetails userDetails)
    {
        final String username = extractUsername(jwt);
        return username.equals(userDetails.getUsername()) && !isJwtExpired(jwt);
    }

    private boolean isJwtExpired(String jwt)
    {
        return extractExpiration(jwt).before(new Date());
    }

    private Date extractExpiration(String jwt)
    {
        return extractClaim(jwt, Claims::getExpiration);
    }

    private Claims extractAllClaims(String jwt)
    {
        return Jwts.parserBuilder()
                .setSigningKey(SIGN_IN_KEY)
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    private Key getSignInKey()
    {

        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Autowired
    private final Environment env;

    private final String SECRET_KEY;
    private final Key SIGN_IN_KEY;
}
