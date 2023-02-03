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
 * 展品别名，一个展品可以通过多个展品别名定位到一个展品
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
@TableName(value = "tbl_exhibit_alias", autoResultMap = true)
public class ExhibitAlias extends BaseEntity {

    @TableId(value = "exhibit_alias_id", type = IdType.AUTO)
    private Long id;

    @TableField("exhibit_id")
    private Long exhibitId;

    @TableField("exhibit_alias")
    private String alias;
}
