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
@TableName("text")
public class Text extends BaseEntity {

    @TableId(value = "text_id", type = IdType.AUTO)
    private Long id;

    @TableField("exhibits_label")
    private String label;

    @TableField("exhibits_text")
    private String text;
}
