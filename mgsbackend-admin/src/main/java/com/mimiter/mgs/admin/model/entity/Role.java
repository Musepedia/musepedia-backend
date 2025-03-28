package com.mimiter.mgs.admin.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mimiter.mgs.common.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@ApiModel(value = "角色实体类")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "name", callSuper = false)
@TableName(value = "sys_role", autoResultMap = true)
public class Role extends BaseEntity {

    @TableId(value = "role_id", type = IdType.AUTO)
    private Long id;

    /**
     * 实际用到权限验证的角色名如：sys_admin, museum_admin
     */
    @ApiModelProperty(value = "实际用到权限验证的角色名",
            example = "sys_admin")
    private String name;

    @ApiModelProperty(value = "角色描述", example = "系统管理员")
    private String description;

}
