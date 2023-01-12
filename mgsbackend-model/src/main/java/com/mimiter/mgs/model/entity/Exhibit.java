package com.mimiter.mgs.model.entity;

import com.mimiter.mgs.common.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 展品实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
@TableName(value = "tbl_exhibit", autoResultMap = true, excludeProperty = {"exhibitionHall", "questionCount"})
public class Exhibit extends BaseEntity {

    @TableId(value = "exhibit_id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "exhibition_hall_id")
    private Long hallId;

    @TableField(value = "museum_id")
    private Long museumId;

    private ExhibitionHall exhibitionHall;

    @TableField(value = "exhibit_figure_url")
    private String figureUrl;

    @TableField(value = "exhibit_label")
    private String label;

    @TableField(value = "exhibit_description")
    private String description;

    @TableField(value = "exhibit_url")
    private String url;

    @TableField(value = "exhibit_is_hot")
    private Boolean hot;

    @TableField(value = "exhibit_prev_id")
    private Long prevId;

    @TableField(value = "exhibit_next_id")
    private Long nextId;

    @TableField(value = "is_enabled")
    private Boolean enabled;

    private Integer questionCount;
}
