package com.mimiter.mgs.model.entity;

import com.mimiter.mgs.common.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * <p>问题反馈，每个用户可以对问题进行反馈，用{@link #feedback}布尔值表示。</p>
 * <p>true表示正面反馈，false表示负面反馈。</p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
@TableName(value = "tbl_user_question", autoResultMap = true)
public class QuestionFeedback extends BaseEntity {

    @TableId(value = "user_question_id", type = IdType.AUTO)
    private Long id;

    private Long userId;

    private Long questionId;

    @NotNull(message = "反馈不能为空")
    private Boolean feedback;
}
