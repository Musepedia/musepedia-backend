package com.mimiter.mgs.admin.service;

import com.mimiter.mgs.admin.model.dto.UserDTO;
import com.mimiter.mgs.admin.model.entity.AdminUser;
import com.mimiter.mgs.admin.model.request.AddUserReq;
import com.mimiter.mgs.admin.model.request.LoginReq;
import com.mimiter.mgs.admin.model.request.UpdateUserReq;
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

    /**
     * 更新用户
     *
     * @param req 更新用户请求
     * @return 是否更新成功
     */
    boolean updateById(UpdateUserReq req);

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
     * @param password 未加密的密码
     */
    void setPassword(Long userId, String password);

    /**
     * 切换用户禁用状态
     *
     * @param userId 用户ID
     */
    void setEnable(Long userId, boolean enable);

    /**
     * 校验用户密码是否正确
     *
     * @param userId   用户ID
     * @param password 未加密的密码
     * @return 密码是否匹配
     */
    boolean checkPassword(Long userId, String password);

    /**
     * 转换为UserDTO，添加角色，InstitutionId信息
     *
     * @param user /
     * @return null如果user是null，否则返回转换后的UserDTO
     */
    UserDTO toDto(AdminUser user);


}
