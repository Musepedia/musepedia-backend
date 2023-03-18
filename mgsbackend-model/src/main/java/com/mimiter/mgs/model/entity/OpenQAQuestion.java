package com.mimiter.mgs.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mimiter.mgs.common.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * OpenQA问答类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
@TableName(value = "tbl_openqa_question", autoResultMap = true)
public class OpenQAQuestion extends BaseEntity {

    @TableId(value = "question_id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "user_question")
    private String question;

    @TableField(value = "open_document_id")
    private Long openDocumentId;

    @TableField(value = "answer")
    private String answer;

    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "museum_id")
    private Long museumId;

    @TableField(value = "feedback")
    private Boolean feedback;

}
