package com.mimiter.mgs.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mimiter.mgs.admin.mapstruct.AdminUserMapper;
import com.mimiter.mgs.admin.model.dto.PageDTO;
import com.mimiter.mgs.admin.model.dto.UserDTO;
import com.mimiter.mgs.admin.model.entity.AdminUser;
import com.mimiter.mgs.admin.model.query.UserQuery;
import com.mimiter.mgs.admin.model.request.AddUserReq;
import com.mimiter.mgs.admin.model.request.SetEnableReq;
import com.mimiter.mgs.admin.model.request.SetPasswordReq;
import com.mimiter.mgs.admin.model.request.UpdateUserReq;
import com.mimiter.mgs.admin.service.AdminUserService;
import com.mimiter.mgs.admin.service.RoleService;
import com.mimiter.mgs.common.model.BaseResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mimiter.mgs.admin.service.RoleService.STR_SYS_ADMIN;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("@pm.check('" + STR_SYS_ADMIN + "')")
public class AdminUserController {

    private final AdminUserService adminUserService;

    private final RoleService roleService;
    private final AdminUserMapper adminUserMapper;

    //-----------Admin Operation------------

    @ApiOperation("查询用户列表")
    @GetMapping
    public BaseResponse<PageDTO<UserDTO>> listUser(@ApiParam UserQuery query) {
        Page<AdminUser> p = adminUserService.page(query.toPage(), query.toQueryWrapper());
        List<UserDTO> dtos = adminUserMapper.toDto(p.getRecords());
        for (UserDTO dto : dtos) {
            dto.setRoles(roleService.listUserRoles(dto.getId()));
        }
        return BaseResponse.ok("ok", new PageDTO<>(dtos, p.getTotal()));
    }

    @ApiOperation("添加用户")
    @PostMapping
    public BaseResponse<?> addUser(@RequestBody @Validated AddUserReq user) {
        adminUserService.addUser(user);
        return BaseResponse.ok();
    }

    @ApiOperation("更新单个用户信息")
    @PutMapping
    public BaseResponse<?> updateUser(@RequestBody @Validated UpdateUserReq user) {
        adminUserService.updateById(user);
        return BaseResponse.ok();
    }

    @ApiOperation("超级管理员重置用户密码")
    @PutMapping("/password")
    public BaseResponse<?> setPassword(@RequestBody @Validated SetPasswordReq req) {
        adminUserService.setPassword(req.getUserId(), req.getPassword());
        return BaseResponse.ok();
    }

    @ApiOperation("切换单个用户启用状态")
    @PutMapping("/enable")
    public BaseResponse<?> setEnable(@RequestBody @Validated SetEnableReq req) {
        adminUserService.setEnable(req.getUserId(), req.getEnable());
        return BaseResponse.ok();
    }
}
