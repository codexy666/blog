package com.study.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.admin.entity.pojo.Permission;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission> {
    List<Permission> findPermissionsByAdminId(Long id);
}
