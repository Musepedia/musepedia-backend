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

    @TableField(value = "museum_id")
    private Long museumId;

    private Integer exhibitCount;

    private Integer questionCount;
}
