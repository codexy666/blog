package com.study.admin.service;

import com.study.admin.entity.param.PageParam;
import com.study.admin.entity.pojo.Permission;
import com.study.admin.entity.vo.Result;


public interface PermissionService {
    Result listPermission(PageParam pageParam);

    Result add(Permission permission);

    Result update(Permission permission);

    Result delete(Long id);
}
