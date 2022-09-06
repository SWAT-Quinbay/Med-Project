package com.example.adminService.services.implement;

import com.example.adminService.repos.redis.TokensRedisRepository;
import com.example.adminService.models.redis.TokensEntity;
import com.example.adminService.services.TokensRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TokenRedisServiceImp implements TokensRedisService {

    @Autowired
    private TokensRedisRepository tokensRedisRepository;

    @Override
    public TokensEntity save(TokensEntity entity) {
        return tokensRedisRepository.save(entity);
    }

    @Override
    public Optional<TokensEntity> findById(String id) {
        return tokensRedisRepository.findById(id);
    }

    @Override
    public Iterable<TokensEntity> findAll() {
        return tokensRedisRepository.findAll();
    }


}
