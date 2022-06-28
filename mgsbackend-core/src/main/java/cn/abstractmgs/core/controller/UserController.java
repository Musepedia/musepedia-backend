package cn.abstractmgs.core.controller;

import cn.abstractmgs.common.annotation.AnonymousAccess;
import cn.abstractmgs.common.model.BaseResponse;
import cn.abstractmgs.core.model.dto.UserDTO;
import cn.abstractmgs.core.model.entity.User;
import cn.abstractmgs.core.model.param.WxLoginParam;
import cn.abstractmgs.core.service.UserPreferenceService;
import cn.abstractmgs.core.service.UserService;
import cn.abstractmgs.core.service.mapstruct.UserDTOMapper;
import cn.abstractmgs.core.utils.SecurityUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserDTOMapper userDTOMapper;

    @Value("${mgs.login-any:false}")
    private Boolean loginAny;

    @AnonymousAccess
    @GetMapping("/info")
    public BaseResponse<UserDTO> getUserInfo(){
        Long userId = SecurityUtil.getCurrentUserId();
        UserDTO dto = userId == null ? null : userDTOMapper.toDto(userService.getById(userId));
        return BaseResponse.ok("ok", dto);
    }

    @AnonymousAccess
    @PostMapping("/login-wx")
    public BaseResponse<UserDTO> login(@Validated @RequestBody WxLoginParam param, HttpServletRequest request){
        User user = userService.loginWx(param);
        if(user != null){
            request.getSession().setAttribute("userId", user.getId());
        }
        return BaseResponse.ok("登录成功", userDTOMapper.toDto(user));
    }

    @ApiOperation("更新用户个人信息")
    @PutMapping("/profile")
    public BaseResponse<?> updateProfile(@RequestBody UserDTO dto){
        // 只更新性别和年龄信息
        User user = new User();
        user.setId(SecurityUtil.getCurrentUserId());
        user.setAge(dto.getAge());
        user.setGender(dto.getGender());
        userService.updateById(user);

        return BaseResponse.ok();
    }

    @AnonymousAccess
    @PostMapping("/login-any")
    public BaseResponse<?> loginAny(HttpServletRequest request){
        if(!loginAny){
            throw new UnsupportedOperationException();
        }
        User u = userService.getOne(new QueryWrapper<>());
        request.getSession().setAttribute("userId", u.getId());
        return BaseResponse.ok("ok", u);
    }

}
