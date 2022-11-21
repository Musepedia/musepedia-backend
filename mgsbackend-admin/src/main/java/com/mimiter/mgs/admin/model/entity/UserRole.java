package com.mimiter.mgs.admin.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mimiter.mgs.common.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 用户角色多对多
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"userId", "roleId"}, callSuper = false)
@TableName(value = "sys_user_role", autoResultMap = true)
public class UserRole extends BaseEntity {

    private Long userId;

    private Long roleId;
}
