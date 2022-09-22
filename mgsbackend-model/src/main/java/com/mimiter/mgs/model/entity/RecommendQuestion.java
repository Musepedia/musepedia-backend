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

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
@TableName(value = "tbl_recommend_question", autoResultMap = true, excludeProperty = "exhibitFigureUrl")
public class RecommendQuestion extends BaseEntity {

    @TableId(value = "question_id", type = IdType.AUTO)
    private Long id;

    @TableField("question_text")
    private String questionText;

    @TableField("answer_type")
    private int answerType;

    @TableField("answer_text")
    private String answerText;

    @TableField("exhibit_id")
    private Long exhibitId;

    @TableField("museum_id")
    private Long museumId;

    private String exhibitFigureUrl;

    @TableField("exhibit_text_id")
    private Long exhibitTextId;

    @TableField("question_freq")
    private int questionFreq;
}
