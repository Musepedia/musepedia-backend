package cn.abstractmgs.core.controller;

import cn.abstractmgs.common.model.BaseResponse;
import cn.abstractmgs.core.model.entity.User;
import cn.abstractmgs.core.model.param.WxLoginParam;
import cn.abstractmgs.core.service.UserService;
import cn.abstractmgs.core.utils.WxUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final WxUtil wxUtil;

    private final UserService userService;

    @PostMapping("/login-wx")
    public BaseResponse<?> login(@Validated @RequestBody WxLoginParam param, HttpServletRequest request){
        User user = userService.loginWx(param);
        if(user != null){
            request.getSession().setAttribute("user", user);
        }
        return BaseResponse.ok("ok");
    }

}
