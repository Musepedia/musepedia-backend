package com.mimiter.mgs.admin.controller;

import com.mimiter.mgs.admin.model.entity.AdminUser;
import com.mimiter.mgs.admin.model.request.LoginReq;
import com.mimiter.mgs.admin.service.AdminUserService;
import com.mimiter.mgs.admin.service.RoleService;
import com.mimiter.mgs.common.annotation.AnonymousAccess;
import com.mimiter.mgs.common.model.BaseResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final AdminUserService userService;

    private final RoleService roleService;

    @ApiOperation("获取当前用户信息")
    @PostMapping("/info")
    @AnonymousAccess
    public BaseResponse<?> info() {
        // TODO
        return BaseResponse.ok();
    }

    @ApiOperation("通过密码登录")
    @PostMapping("/login")
    @AnonymousAccess
    public BaseResponse<?> loginPassword(@Validated LoginReq req) {
        AdminUser user = userService.loginPassword(req);
        return BaseResponse.ok();
    }

    @ApiOperation("登出")
    @PostMapping("/logout")
    public BaseResponse<?> logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return BaseResponse.ok();
    }
}
