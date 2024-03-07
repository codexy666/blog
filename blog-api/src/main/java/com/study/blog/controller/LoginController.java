package com.study.blog.controller;

import com.study.blog.entity.params.LoginParam;
import com.study.blog.entity.vo.Result;
import com.study.blog.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("login")
    public Result login(@RequestBody LoginParam loginParam) {
        System.out.println(loginParam);
        return loginService.login(loginParam);
    }
}
