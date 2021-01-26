package com.leverx.blog.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
public class CodeRepositoryImpl implements CodeRepository {
    private RedisTemplate<String, String> redisTemplate;

    private final HashOperations hashOperations;

    public CodeRepositoryImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
        redisTemplate.expire("CODS",1, TimeUnit.DAYS);
        hashOperations = redisTemplate.opsForHash();
    }


    @Override
    public void save(String code, String email) {
        hashOperations.put("CODS", code, email);

    }

    @Override
    public Optional<String> findByCode(String code) {
        return Optional.ofNullable((String) hashOperations.get("CODS",code));

    }
}
