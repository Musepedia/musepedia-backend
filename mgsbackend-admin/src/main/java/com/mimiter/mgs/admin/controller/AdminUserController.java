package com.mimiter.mgs.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mimiter.mgs.admin.model.dto.PageDTO;
import com.mimiter.mgs.admin.model.dto.UserDTO;
import com.mimiter.mgs.admin.model.entity.AdminUser;
import com.mimiter.mgs.admin.model.query.UserQuery;
import com.mimiter.mgs.admin.service.UserService;
import com.mimiter.mgs.common.model.BaseResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/user")
@RequiredArgsConstructor
@PreAuthorize("@pm.check('sys_admin')")
public class AdminUserController {

    private final UserService userService;

    //-----------Admin Operation------------

    @GetMapping
    public BaseResponse<PageDTO<UserDTO>> listUser(Page<AdminUser> page, UserQuery query) {
        Page<AdminUser> p = userService.page(page, query.toQueryWrapper());
        return BaseResponse.ok("ok", new PageDTO<>(userService.toDto(p.getRecords()), p.getTotal()));
    }

    @PostMapping
    public BaseResponse<?> addUser(@RequestBody @Validated AdminUser user) {
        userService.save(user);
        return BaseResponse.ok();
    }

    @PostMapping("/reset-password")
    public BaseResponse<?> resetPassword(@RequestBody Long userId) {
        userService.resetPassword(userId);
        return BaseResponse.ok();
    }

    @ApiOperation("更新单个用户信息")
    @PutMapping
    public BaseResponse<?> updateUser(@RequestBody @Validated AdminUser user) {
        // 不更新密码
        user.setPassword(null);
        userService.updateById(user);
        return BaseResponse.ok();
    }

    @DeleteMapping
    public BaseResponse<?> deleteUser(@RequestBody String userId) {
        userService.removeById(userId);
        return BaseResponse.ok();
    }
}
