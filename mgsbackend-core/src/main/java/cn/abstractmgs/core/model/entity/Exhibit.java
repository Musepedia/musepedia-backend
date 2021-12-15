package cn.abstractmgs.core.model.entity;

import cn.abstractmgs.common.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
@TableName(value = "tbl_exhibit",autoResultMap = true)
public class Exhibit extends BaseEntity {

    @TableId(value = "exhibit_id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "exhibit_label")
    private String label;

    @TableField(value = "exhibit_category")
    private String category;

    @TableField(value = "exhibit_description")
    private String description;

    @TableField(value = "exhibit_url")
    private String url;
}
