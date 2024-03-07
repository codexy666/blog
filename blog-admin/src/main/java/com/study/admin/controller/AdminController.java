package com.study.admin.controller;

import com.study.admin.entity.param.PageParam;
import com.study.admin.entity.pojo.Permission;
import com.study.admin.entity.vo.Result;
import com.study.admin.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private PermissionService permissionService;

    @PostMapping("permission/permissionList")
    public Result PermissionList(@RequestBody PageParam pageParam) {
        return permissionService.listPermission(pageParam);
    }

    @PostMapping("permission/add")
    public Result add(@RequestBody Permission permission) {
        return permissionService.add(permission);
    }

    @PostMapping("permission/update")
    public Result update(@RequestBody Permission permission) {
        return permissionService.update(permission);
    }

    @PostMapping("permission/delete/{id}")
    public Result delete(@PathVariable Long id) {
        return permissionService.delete(id);
    }
}
