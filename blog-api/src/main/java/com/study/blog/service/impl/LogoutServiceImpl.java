package com.study.blog.service.impl;

import com.study.blog.entity.vo.Result;
import com.study.blog.service.LogoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class LogoutServiceImpl implements LogoutService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    
    @Override
    public Result logout(String token) {
        redisTemplate.delete("TOKEN_" + token);
        return Result.success(null);
    }
}
