package com.mimiter.mgs.model.entity;

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
 * 博物馆实体类，一个博物馆有多个展区，每个展区有多个展品
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
@TableName(value = "tbl_museum", autoResultMap = true)
public class Museum extends BaseEntity {

    public static final int PERMISSION_OPEN_QA = 1 << 0;
    public static final int PERMISSION_GPT = 1 << 1;

    @TableId(value = "museum_id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "museum_name")
    private String name;

    @TableField(value = "museum_description")
    private String description;

    @TableField(value = "museum_logo_url")
    private String logoUrl;

    @TableField(value = "museum_image_url")
    private String imageUrl;

    @TableField(value = "address")
    private String address;

    @TableField(value = "museum_is_service")
    private Boolean service;

    @TableField(value = "museum_floor_plan_filepath")
    private String floorPlanFilepath;

    @TableField(value = "longitude")
    private Double longitude;

    @TableField(value = "latitude")
    private Double latitude;

    /**
     * QA的权限，默认只允许robert(0)，还有OpenQA以及ChatGPT可选
     */
    @TableField(value = "permission")
    private int permission;

    public void setPermission(int flag, boolean allow) {
        this.permission = allow ? this.permission | flag : this.permission & (~flag);
    }

    public boolean hasPermission(int flag) {
        return (permission & flag) > 0;
    }

}
