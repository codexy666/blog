package com.study.blog.service;

import com.study.blog.entity.pojo.SysUser;
import com.study.blog.entity.vo.Result;
import com.study.blog.entity.vo.UserVo;

public interface SysUserService {
    SysUser findSysUserById(Long userId);

    SysUser findUser(String account, String pwd);

    Result getSysUserByToken(String token);

    SysUser findSysUserByAccount(String account);

    void save(SysUser sysUser);

    UserVo findUserVoById(Long userId);
}
