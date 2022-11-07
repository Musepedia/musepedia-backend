package com.mimiter.mgs.core.controller;

import com.mimiter.mgs.common.annotation.AnonymousAccess;
import com.mimiter.mgs.common.model.BaseResponse;
import com.mimiter.mgs.core.model.dto.UserDTO;
import com.mimiter.mgs.core.model.entity.User;
import com.mimiter.mgs.core.model.param.PhoneLoginParam;
import com.mimiter.mgs.core.model.param.WxLoginParam;
import com.mimiter.mgs.core.service.UserService;
import com.mimiter.mgs.core.service.mapstruct.UserDTOMapper;
import com.mimiter.mgs.common.utils.EnvironmentUtil;
import com.mimiter.mgs.core.utils.SecurityUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户相关接口
 *
 * @author Uzemiu
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserDTOMapper userDTOMapper;

    @ApiOperation(value = "获取用户个人信息", notes = "未登录返回null")
    @AnonymousAccess
    @GetMapping("/info")
    public BaseResponse<UserDTO> getUserInfo() {
        Long userId = SecurityUtil.getCurrentUserId();
        UserDTO dto = userId == null ? null : userDTOMapper.toDto(userService.getById(userId));
        return BaseResponse.ok("ok", dto);
    }

    @ApiOperation("通过手机号登录")
    @AnonymousAccess
    @PostMapping("/login-phone")
    public BaseResponse<UserDTO> loginPhone(PhoneLoginParam param, HttpServletRequest request) {
        User user = userService.loginPhone(param);
        if (user != null) {
            request.getSession().setAttribute("userId", user.getId());
        }
        return BaseResponse.ok("登录成功", userDTOMapper.toDto(user));
    }

    @ApiOperation("通过微信登录")
    @AnonymousAccess
    @PostMapping("/login-wx")
    public BaseResponse<UserDTO> login(@Validated @RequestBody WxLoginParam param, HttpServletRequest request) {
        User user = userService.loginWx(param);
        if (user != null) {
            request.getSession().setAttribute("userId", user.getId());
        }
        return BaseResponse.ok("登录成功", userDTOMapper.toDto(user));
    }

    @ApiOperation("更新用户个人信息")
    @PutMapping("/profile")
    public BaseResponse<?> updateProfile(@RequestBody UserDTO dto) {
        // 只更新性别和年龄信息
        User user = new User();
        user.setId(SecurityUtil.getCurrentUserId());
        user.setAge(dto.getAge());
        user.setGender(dto.getGender());
        userService.updateById(user);

        return BaseResponse.ok();
    }

    @ApiOperation("测试环境下登录")
    @AnonymousAccess
    @PostMapping("/login-any")
    public BaseResponse<?> loginAny(HttpServletRequest request) {
        if (!EnvironmentUtil.isTestEnv()) {
            throw new UnsupportedOperationException();
        }
        Page<User> page = new Page<>();
        page.setSize(1);
        User u = userService.page(page).getRecords().get(0);
        request.getSession().setAttribute("userId", u.getId());
        return BaseResponse.ok("ok", u);
    }

}
