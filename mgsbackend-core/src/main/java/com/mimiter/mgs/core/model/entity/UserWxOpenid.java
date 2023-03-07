package com.mimiter.mgs.core.model.entity;

import com.mimiter.mgs.common.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>用户微信unionId到用户的一对一映射</p>
 * <p>注：openId字段实际为unionId</p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
@TableName("tbl_user_wx_openid")
public class UserWxOpenid extends BaseEntity {

    @TableId(value = "user_wx_openid_id", type = IdType.AUTO)
    private Long id;

    @TableField("user_id")
    private Long userId;

    @TableField("wx_openid")
    private String wxOpenId;

    @TableField("wx_unionid")
    private String wxUnionId;
}
