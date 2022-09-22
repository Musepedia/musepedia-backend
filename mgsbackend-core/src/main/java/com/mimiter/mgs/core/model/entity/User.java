package com.mimiter.mgs.core.model.entity;

import com.mimiter.mgs.common.model.BaseEntity;
import com.mimiter.mgs.core.model.entity.enums.AgeEnum;
import com.mimiter.mgs.core.model.entity.enums.GenderEnum;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
@TableName(value = "tbl_user", autoResultMap = true)
public class User extends BaseEntity {

    @TableId(value = "user_id", type = IdType.AUTO)
    private Long id;

    @TableField("nickname")
    private String nickname;

    @TableField("phone_number")
    private String phoneNumber;

    @TableField("avatar_url")
    private String avatarUrl;

    @TableField("gender")
    private GenderEnum gender;

    @TableField("age")
    private AgeEnum age;
}
