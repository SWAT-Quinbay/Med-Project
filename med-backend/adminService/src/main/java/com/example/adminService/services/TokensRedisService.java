package com.example.adminService.services;

import com.example.adminService.models.redis.TokensEntity;

import java.util.Optional;

public interface TokensRedisService {

    TokensEntity save(TokensEntity tokensEntity);
    Optional<TokensEntity> findById(String id);
    Iterable<TokensEntity> findAll();
}
