package com.mimiter.mgs.admin.model.request;

import lombok.Data;

/**
 * 更新用户个人信息请求参数
 */
@Data
public class UpdateUserInfoReq {

    private String email;

    private String phone;

    private String nickname;
}
