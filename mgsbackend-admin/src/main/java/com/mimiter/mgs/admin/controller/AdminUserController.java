package com.mimiter.mgs.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mimiter.mgs.admin.mapstruct.AdminUserMapper;
import com.mimiter.mgs.admin.model.dto.PageDTO;
import com.mimiter.mgs.admin.model.dto.UserDTO;
import com.mimiter.mgs.admin.model.entity.AdminUser;
import com.mimiter.mgs.admin.model.query.UserQuery;
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

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("@pm.check('sys_admin')")
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

    @PostMapping
    public BaseResponse<?> addUser(@RequestBody @Validated AdminUser user) {
        adminUserService.save(user);
        return BaseResponse.ok();
    }

    @PostMapping("/reset-password")
    public BaseResponse<?> resetPassword(@RequestBody Long userId) {
        // TODO
        return BaseResponse.ok();
    }

    @ApiOperation("更新单个用户信息")
    @PutMapping
    public BaseResponse<?> updateUser(@RequestBody @Validated AdminUser user) {
        // TODO
        // 不更新密码
        user.setPassword(null);
        // 不更新状态
        user.setEnabled(null);
        adminUserService.updateById(user);
        return BaseResponse.ok();
    }

}
