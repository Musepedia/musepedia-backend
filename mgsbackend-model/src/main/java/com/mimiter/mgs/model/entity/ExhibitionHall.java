package com.mimiter.mgs.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.mimiter.mgs.common.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 展厅实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
@TableName(value = "tbl_exhibition_hall", autoResultMap = true, excludeProperty = {"exhibitCount", "questionCount"})
public class ExhibitionHall extends BaseEntity {

    @TableId(value = "exhibition_hall_id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "exhibition_hall_name")
    private String name;

    @TableField(value = "exhibition_hall_description")
    private String description;

    @TableField(value = "image_url")
    private String imageUrl;

    @TableField(value = "image_list", typeHandler = JacksonTypeHandler.class)
    private List<String> imageList;

    @TableField(value = "museum_id")
    private Long museumId;

    @TableField(value = "is_enabled")
    private Boolean enabled;

    private Integer exhibitCount;

    private Integer questionCount;
}
