package com.study.blog.controller;

import com.study.blog.entity.vo.Result;
import com.study.blog.service.LogoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("logout")
public class LogoutController {

    @Autowired
    private LogoutService logoutService;

    @GetMapping()
    public Result logout(@RequestHeader("Authorization") String token) {
        return logoutService.logout(token);
    }
}
