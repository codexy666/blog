package com.study.blog.service;

import com.study.blog.entity.params.LoginParam;
import com.study.blog.entity.pojo.SysUser;
import com.study.blog.entity.vo.Result;

public interface LoginService {
    Result login(LoginParam loginParam);

    SysUser checkToken(String token);
}
