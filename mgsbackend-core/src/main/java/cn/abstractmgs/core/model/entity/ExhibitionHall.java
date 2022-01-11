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
@TableName(value = "tbl_exhibition_hall",autoResultMap = true)
public class ExhibitionHall extends BaseEntity {

    @TableId(value = "exhibition_hall_id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "exhibition_hall_name")
    private String name;

    @TableField(value = "exhibition_hall_description")
    private String description;

    @TableField(value = "museum_id")
    private Long museumId;

    public ExhibitionHall(String exhibitionHallName) {
        this.name = exhibitionHallName;
    }

    public ExhibitionHall(String exhibitionHallName, String exhibitionDescription) {
        this.name = exhibitionHallName;
        this.description = exhibitionDescription;
    }
}
