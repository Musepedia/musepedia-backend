package cn.abstractmgs.label.model.entity;

import cn.abstractmgs.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "id")
@TableName(value = "paragraph", resultMap = "paragraphMapper")
public class Paragraph extends BaseEntity {

    @TableId(value = "paragraph_id", type = IdType.AUTO)
    private Long id;

    @TableField("text")
    private String text;

    @TableField(exist = false)
    private List<Question> questions;
}
