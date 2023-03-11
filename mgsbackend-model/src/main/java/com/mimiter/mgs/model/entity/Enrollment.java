package com.mimiter.mgs.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mimiter.mgs.common.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * 活动报名信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
@TableName(value = "enrollment", autoResultMap = true)
public class Enrollment extends BaseEntity {

    @TableId(value = "enrollment_id", type = IdType.AUTO)
    private Long id;

    @TableField("user_id")
    private Long userId;

    @TableField("phone")
    private String phone;

    @TableField("event_id")
    private Long eventId;

    @TableField("out_trade_no")
    private String outTradeNo;

    /**
     * 未付款
     * 报名成功等
     */
    @TableField("state")
    private Integer state;
}
