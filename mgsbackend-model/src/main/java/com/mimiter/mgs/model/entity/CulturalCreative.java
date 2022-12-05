package com.mimiter.mgs.model.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.mimiter.mgs.common.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 文创产品
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
@TableName(value = "tbl_creative", autoResultMap = true)
public class CulturalCreative extends BaseEntity {

    @TableField("creative_id")
    private Long id;

    private String name;

    private String description;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> imgs;

    private Long museumId;
}
