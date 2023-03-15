package com.mimiter.mgs.model.entity;

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
@TableName(value = "tbl_gpt_completion", autoResultMap = true)
public class GPTCompletion {

    @TableId(value = "gpt_completion_id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "user_question")
    private String userQuestion;

    @TableField(value = "gpt_prompt")
    private String prompt;

    @TableField(value = "gpt_completion")
    private String completion;

    @TableField(value = "prompt_tokens")
    private Integer promptTokens;

    @TableField(value = "completion_tokens")
    private Integer completionTokens;

    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "feedback")
    private Boolean feedback;
}
