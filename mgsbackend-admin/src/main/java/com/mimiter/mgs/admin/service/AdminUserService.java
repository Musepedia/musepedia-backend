package com.mimiter.mgs.admin.service;

import com.mimiter.mgs.admin.model.entity.AdminUser;
import com.mimiter.mgs.admin.model.request.AddUserReq;
import com.mimiter.mgs.admin.model.request.LoginReq;
import com.mimiter.mgs.admin.model.request.ResetPasswordParam;
import com.mimiter.mgs.admin.service.base.CrudService;
import com.mimiter.mgs.common.exception.BadRequestException;

/**
 * 用户服务
 */
public interface AdminUserService extends CrudService<AdminUser> {

    /**
     * 添加用户
     *
     * @param req 添加用户请求
     * @return 添加的用户
     */
    AdminUser addUser(AddUserReq req);

    AdminUser findByUsername(String username);

    /**
     * 通过密码登录
     *
     * @param param /
     * @return 登录的用户
     * @throws BadRequestException 校验验证码失败
     */
    AdminUser loginPassword(LoginReq param) throws BadRequestException;


    /**
     * 用户设置新密码
     *
     * @param param /
     */
    void resetPassword(Long userId, ResetPasswordParam param);

}
