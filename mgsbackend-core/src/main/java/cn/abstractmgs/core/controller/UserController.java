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
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserPreferenceService userPreferenceService;

    private final UserDTOMapper userDTOMapper;

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
        return BaseResponse.ok("登陆成功", userDTOMapper.toDto(user));
    }

    @PostMapping("update-preference")
    public BaseResponse<String> updatePreference(@RequestBody List<Integer> hallIds) {
         Long userId = SecurityUtil.getCurrentUserId();
         System.out.println(userId);
         boolean isUpdated = userPreferenceService.updateUserPreference(userId, hallIds);

        return isUpdated
                ? BaseResponse.ok("ok", "用户偏好更新成功")
                : BaseResponse.error("error", "用户偏好更新失败");
    }
}
