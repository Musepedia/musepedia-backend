package com.mimiter.mgs.admin.controller;

import com.mimiter.mgs.admin.model.entity.Role;
import com.mimiter.mgs.admin.service.RoleService;
import com.mimiter.mgs.common.model.BaseResponse;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.mimiter.mgs.admin.service.RoleService.STR_SYS_ADMIN;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Api(value = "角色API", tags = {"角色API"})
@PreAuthorize("@pm.check('" + STR_SYS_ADMIN + "')")
public class RoleController {

    private final RoleService roleService;

    @ApiOperation(value = "获取角色列表", notes = "仅超级管理员可调用")
    @GetMapping("/list")
    public BaseResponse<List<Role>> listRoles() {
        return BaseResponse.ok(roleService.list());
    }
}
