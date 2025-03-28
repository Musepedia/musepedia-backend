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

import java.util.Date;

/**
 * 活动信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
@TableName(value = "muse_event", autoResultMap = true)
public class MuseEvent extends BaseEntity {

    @TableId(value = "event_id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "name")
    private String name;

    @TableField(value = "description")
    private String description;

    @TableField(value = "link")
    private String link;

    @TableField(value = "img")
    private String img;

    /**
     * 1: 博物馆活动
     */
    @TableField(value = "type")
    private Integer type;

    @TableField(value = "start_time")
    private Date startTime;

    @TableField(value = "end_time")
    private Date endTime;

    /**
     * 活动报名费用，单位分
     */
    @TableField("price")
    private Integer price;

    /**
     * 活动人数上限
     */
    @TableField("limit")
    private Integer limit;
}
