package cn.abstractmgs.core.model.entity;

import cn.abstractmgs.common.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.One;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
@TableName(value = "tbl_exhibit", autoResultMap = true, excludeProperty = "exhibitionHall")
public class Exhibit extends BaseEntity {

    @TableId(value = "exhibit_id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "exhibition_hall_id")
    private Long hallId;

    private ExhibitionHall exhibitionHall;

    @TableField(value = "exhibit_figure_url")
    private String figureUrl;

    @TableField(value = "exhibit_label")
    private String label;

    @TableField(value = "exhibit_category")
    private String category;

    @TableField(value = "exhibit_description")
    private String description;

    @TableField(value = "exhibit_url")
    private String url;

    @TableField(value = "exhibit_is_hot")
    private Boolean hot;
}
