package com.study.admin.service;

import com.study.admin.entity.pojo.Admin;
import com.study.admin.entity.pojo.Permission;

import java.util.List;

public interface AdminService {
    Admin findAdminByUsername(String username);

    List<Permission> findPermissionsByAdminId(Long id);
}
