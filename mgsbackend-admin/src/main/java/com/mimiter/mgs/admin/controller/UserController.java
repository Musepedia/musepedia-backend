package com.mimiter.mgs.admin.controller;

import com.mimiter.mgs.admin.mapstruct.AdminUserMapper;
import com.mimiter.mgs.admin.model.dto.UserDTO;
import com.mimiter.mgs.admin.model.entity.AdminUser;
import com.mimiter.mgs.admin.model.request.LoginReq;
import com.mimiter.mgs.admin.model.request.ResetPasswordReq;
import com.mimiter.mgs.admin.model.request.UpdateUserInfoReq;
import com.mimiter.mgs.admin.service.AdminUserService;
import com.mimiter.mgs.admin.service.RoleService;
import com.mimiter.mgs.admin.utils.SecurityUtil;
import com.mimiter.mgs.common.annotation.AnonymousAccess;
import com.mimiter.mgs.common.model.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Api(value = "一般用户API", tags = {"一般用户API"})
public class UserController {

    private final AdminUserService userService;

    private final AdminUserMapper userMapper;

    private final RoleService roleService;

    @ApiOperation(value = "获取当前用户信息", notes = "如果未登录，data为null")
    @GetMapping("/info")
    @AnonymousAccess
    public BaseResponse<UserDTO> info() {
        Long userId = SecurityUtil.getCurrentUserId();
        if (userId == null) {
            return BaseResponse.ok();
        }
        AdminUser user = userService.getById(userId);
        if (user == null) {
            return BaseResponse.ok();
        }
        UserDTO userDto = userMapper.toDto(user);
        userDto.setRoles(roleService.listUserRoles(userId));
        return BaseResponse.ok(userDto);
    }

    @ApiOperation("通过密码登录")
    @PostMapping("/login")
    @AnonymousAccess
    public BaseResponse<UserDTO> loginPassword(@RequestBody @Validated LoginReq req, HttpServletRequest request) {
        AdminUser user = userService.loginPassword(req);
        UserDTO userDto = userMapper.toDto(user);
        userDto.setRoles(roleService.listUserRoles(user.getId()));
        return BaseResponse.ok(userDto);
    }

    @ApiOperation("重置密码")
    @PostMapping("/reset-password")
    public BaseResponse<?> resetPassword(@RequestBody @Validated ResetPasswordReq req) {
        Long userId = SecurityUtil.getCurrentUserId();
        Assert.isTrue(userService.checkPassword(userId, req.getOldPassword()), "原密码错误");
        userService.setPassword(userId, req.getNewPassword());
        return BaseResponse.ok();
    }

    @ApiOperation("登出")
    @PostMapping("/logout")
    public BaseResponse<?> logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return BaseResponse.ok();
    }

    @ApiOperation("修改个人信息")
    @PutMapping("/info")
    public BaseResponse<?> updateInfo(@RequestBody UpdateUserInfoReq req) {
        Long userId = SecurityUtil.getCurrentUserId();
        // 对于user中其他为null的信息不会修改
        AdminUser user = new AdminUser();
        user.setId(userId);
        user.setNickname(req.getNickname());
        user.setEmail(req.getEmail());
        user.setPhone(req.getPhone());
        userService.updateById(user);

        return BaseResponse.ok("修改成功");
    }
}
