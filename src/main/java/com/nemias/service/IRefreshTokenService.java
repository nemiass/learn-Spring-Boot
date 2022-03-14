package com.nemias.service;

import com.nemias.dto.TokensDto;

public interface IRefreshTokenService {
    TokensDto refreshToken(String authorizationHeader);
}
