package com.nemias.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    @Value("${security.jwt.secret}")
    private String secretKey;

    @Value("${security.jwt.issuer}")
    private String issuer;

    @Value("${security.jwt.ttlMillis}")
    private Long ttlMillis;

    public String generateAccesToken(User user) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + ttlMillis))
                .withIssuer(issuer)
                .withClaim("roles", user.getAuthorities()
                        .stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(getAlgorithm());
    }

    public String generateRefreshToken(User user) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + ttlMillis + 2 * 60 * 1000))
                .withIssuer(issuer)
                .sign(getAlgorithm());
    }

    public String resolveBarerToken(String authorizationHeader) {
        return authorizationHeader.substring("Bearer ".length());
    }

    public String getSecretKey() {
        return secretKey;
    }

    public Algorithm getAlgorithm() {
        return Algorithm.HMAC256(getSecretKey().getBytes());
    }
}
