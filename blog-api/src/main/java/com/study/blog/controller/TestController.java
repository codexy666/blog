package com.study.blog.controller;

import com.study.blog.entity.pojo.SysUser;
import com.study.blog.entity.vo.Result;
import com.study.blog.utils.UserThreadLocal;
import org.apache.catalina.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @RequestMapping()
    public Result test() {
        // 拦截器后，获取 SysUser
        SysUser sysUser = UserThreadLocal.get();
        System.out.println(sysUser);
        return Result.success(null);
    }
}
