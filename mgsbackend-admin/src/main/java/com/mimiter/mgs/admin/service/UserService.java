package com.mimiter.mgs.admin.service;

import com.mimiter.mgs.admin.model.dto.UserDTO;
import com.mimiter.mgs.admin.model.entity.AdminUser;
import com.mimiter.mgs.admin.model.param.LoginParam;
import com.mimiter.mgs.admin.model.param.ResetPasswordParam;
import com.mimiter.mgs.admin.service.base.CrudService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户服务
 */
public interface UserService extends CrudService<AdminUser> {

    /**
     * 登出
     */
    void logout(HttpServletRequest request);

    AdminUser findByUsername(String username);

    /**
     * 通过密码登录
     *
     * @param param /
     * @return 登录的用户
     */
    AdminUser loginPassword(LoginParam param);

    /**
     * 用户通过邮箱重置密码
     *
     * @param param /
     */
    void forgetPassword(ResetPasswordParam param);

    /**
     * 用户设置新密码
     *
     * @param param /
     */
    void resetPassword(Long userId, ResetPasswordParam param);

    /**
     * 管理员重置userId用户密码为默认密码
     *
     * @param userId /
     */
    void resetPassword(Long userId);

    UserDTO toDto(AdminUser user);

    List<UserDTO> toDto(List<AdminUser> users);

}
