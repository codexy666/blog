package com.study.blog.controller;

import com.study.blog.entity.params.LoginParam;
import com.study.blog.entity.params.RegisterParam;
import com.study.blog.entity.vo.Result;
import com.study.blog.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("register")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @PostMapping
    public Result register(@RequestBody RegisterParam registerParam) {
        return registerService.register(registerParam);
    }
}
