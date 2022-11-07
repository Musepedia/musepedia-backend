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
 * 展品文本信息，一个展品对应多个展品文本信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
@TableName(value = "tbl_exhibit_text", autoResultMap = true)
public class ExhibitText extends BaseEntity {

    @TableId(value = "exhibit_text_id", type = IdType.AUTO)
    private Long id;

    @TableField("exhibit_id")
    private Long exhibitId;

    @TableField("exhibit_text")
    private String text;
}
