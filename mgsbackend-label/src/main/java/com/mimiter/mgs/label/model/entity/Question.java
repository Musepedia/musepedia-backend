package com.mimiter.mgs.label.model.entity;

import com.mimiter.mgs.common.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "id")
@TableName(value = "question", resultMap = "questionMapper")
public class Question extends BaseEntity {

    @TableId(value = "question_id", type = IdType.AUTO)
    private Long id;

    @TableField("text")
    private String text;

    @TableField(value = "paragraph_id", property = "paragraph.id")
    private Paragraph paragraph;

    @TableField(exist = false)
    private List<Answer> answers;
}
