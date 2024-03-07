package com.study.blog.service;

import com.study.blog.entity.vo.Result;

public interface LogoutService {
    Result logout(String token);
}
