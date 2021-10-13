package cn.abstractmgs.label.model.entity;

import cn.abstractmgs.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "id")
@TableName(value = "answer", resultMap = "answerMapper")
public class Answer extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("text")
    private String text;

    @TableField("start_index")
    private Integer startIndex;

    @TableField(value = "question_id", property = "question.id")
    private Question question;
}
