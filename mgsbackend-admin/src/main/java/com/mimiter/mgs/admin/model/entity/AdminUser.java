package com.mimiter.mgs.admin.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mimiter.mgs.common.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@ApiModel(value = "后台用户实体类")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "userId", callSuper = false)
@TableName(value = "sys_user", autoResultMap = true)
public class AdminUser extends BaseEntity {

    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    private String password;

    private String email;

    private String phone;

    private String username;

    @JsonIgnore
    @TableField("is_deleted")
    @TableLogic
    private Boolean deleted;

}
