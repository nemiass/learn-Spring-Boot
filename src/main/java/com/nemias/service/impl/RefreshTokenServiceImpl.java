package com.nemias.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.google.common.base.Strings;
import com.nemias.dto.TokensDto;
import com.nemias.service.IRefreshTokenService;
import com.nemias.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@Service
public class RefreshTokenServiceImpl implements IRefreshTokenService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public TokensDto refreshToken(String authorizationHeader) {
        if (Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Refresh token no valido");
        }
        String refresh_token = jwtUtil.resolveBarerToken(authorizationHeader);
        JWTVerifier verifier = JWT.require(jwtUtil.getAlgorithm()).build();
        DecodedJWT decodedJWT = verifier.verify(refresh_token);

        User usuario = (User) userDetailsService.loadUserByUsername(decodedJWT.getSubject());

        return new TokensDto(jwtUtil.generateRefreshToken(usuario), refresh_token);
    }
}
