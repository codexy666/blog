package com.study.blog.utils;

import com.study.blog.entity.pojo.SysUser;
import org.apache.catalina.User;

public class UserThreadLocal {

    // 饿汉单例模式
    private UserThreadLocal() {}

    // 作为 ThreadLocalMap的key
    private static final ThreadLocal<SysUser> LOCAL = new ThreadLocal<>();

    public static void put(SysUser sysUser) {
        LOCAL.set(sysUser);
    }

    public static SysUser get() {
        return LOCAL.get();
    }

    public static void remove() {
        LOCAL.remove();
    }
}
